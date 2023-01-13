package com.example.trainogram.chat.util;


import com.example.trainogram.chat.model.*;
import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status452ChatAlreadyExistException;
import com.example.trainogram.exception.Status453ChatNotFoundException;
import com.example.trainogram.model.User;
import com.example.trainogram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
@Qualifier("chatService")
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @SneakyThrows
    public List<ChatMessage> save(NewChatMessage incomeMessage) {
        User sender = userService.findById(incomeMessage.getSenderId());
        if (sender == null) {
            throw new Status434UserNotFound(
                    "Can't save incoming chat message because user not found. UserId: " + incomeMessage.getSenderId());
        }

        List<ChatMessage> chatMessages = new ArrayList<>();

        List<User> recipients = chatRoomRepository.findByChatIdaAndSenderId(incomeMessage.getChatId(), sender.getId()).get().getUsers();
        recipients
                .forEach(recipient -> {
                    chatMessages.add(chatMessageRepository.save(ChatMessage.builder()
                            .chatId(incomeMessage.getChatId())
                            .recipientId(recipient.getId())
                            .senderId(sender.getId())
                            .content(incomeMessage.getContent())
                            .status(sender.getId().equals(recipient.getId()) ? ChatMessageStatus.DELIVERED : ChatMessageStatus.RECEIVED)
                            .build()));
                });
        ChatRoom chatRoom = chatRoomRepository.findByChatIdaAndSenderId(incomeMessage.getChatId(), sender.getId()).get();
//        ChatMessage chatRoomFE = chatFEConverter.convertChatRoomToFE(chatRoom);

        new Thread(() -> recipients.stream()
                .filter(recipient -> !sender.getId().equals(recipient.getId()))
                .forEach(recipient -> {
//                    chatRoomFE.se tNewMessageCount(chatMessageRepository.countChatMessagesByChatIdAndRecipientIdAndStatus(chatRoom.getChatId(), recipient.getId(), ChatMessageStatus.RECEIVED));
                    ChatMessage chatMessage = chatMessageRepository.findFirstByChatIdOrderByIdDesc(chatRoom.getChatId());
//                    chatRoomFE.setLastMessage(chatFEConverter.convertChatMessageToFe(chatMessage));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })).start();

        return chatMessages;
    }
    public Long createNewChat(Long recipientId, String senderToken) throws Status452ChatAlreadyExistException, Status434UserNotFound {
        User sender = userService.findAuthenticatedUser(senderToken);
        User recipient = userService.findById(recipientId);
        Long chatId = getChatId()+1;

        List<ChatRoom> existingChatRoom = chatRoomRepository.findAllByChatId(chatId).get();
        if (existingChatRoom.size() > 0) throw new Status452ChatAlreadyExistException();

        ChatRoom chatRoom = ChatRoom
                .builder()
                .recipientId(recipient.getId())
                .senderId(sender.getId())
                .chatId(chatId)
                .build();
        chatRoomRepository.save(chatRoom);
        userChatRoomRepository.save(UserChatRoom.builder()
                .chatRoom(chatRoom)
                .user(sender)
                .build());
        userChatRoomRepository.save(UserChatRoom.builder()
                .chatRoom(chatRoom)
                .user(recipient)
                .build());

        return chatId;
    }

    public Long getChatId() {
        Long id = chatRoomRepository.getMaxChatId();
        return id == null ? 1 : id;
    }


    public List<ChatMessage> findChatMessagesAndMarkDelivered(Long chatId, String token) {
        User recipient = userService.findAuthenticatedUser(token);
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatIdAndRecipientId(chatId, recipient.getId());
        if (chatMessages.size() > 0) {
            chatMessages.forEach(chatMessage -> chatMessage.setStatus(ChatMessageStatus.DELIVERED));
            chatMessageRepository.saveAll(chatMessages);
            chatMessages.sort(Comparator.comparingLong(ChatMessage::getId).reversed());
            return Arrays.asList(modelMapper.map(chatMessages, ChatMessage[].class));
        } else {
            return new ArrayList<>();
        }
    }

    public void deleteChat(Long chatId, String token) throws Status453ChatNotFoundException {
        List<ChatRoom> chat = chatRoomRepository.findAllByChatId(chatId).orElseThrow(()->new Status453ChatNotFoundException(chatId));

        User user = userService.findAuthenticatedUser(token);

        if (isParticipant(chat,user)){
            chatRoomRepository.deleteAll(chat);
        }



    }

    private boolean isParticipant(List<ChatRoom> chat, User user) {
        for (ChatRoom chatRoom : chat) {
            if (chatRoom.getUsers().contains(user)) return true;
        }
        return false;
    }


    public ChatRoom getChatroomById(Long chatroomId) {
        return chatRoomRepository.findById(chatroomId).orElseThrow(IllegalArgumentException::new);
    }
    public ChatRoom getChatRoomByChatIdAndSenderId(Long chatId, Long senderId) {
        return chatRoomRepository.getChatRoomByChatIdAndSenderId(chatId, senderId);
    }


    public void sendMessage(ChatMessage messageObj) {
        messageObj.setStatus(ChatMessageStatus.DELIVERED);
        chatMessageRepository.save(messageObj);
    }

    public User getUserByChatroomId(Long chatRoomId) throws Status434UserNotFound {
        long userId = chatRoomRepository.findSenderById(chatRoomId);
        return userService.findById(userId);
    }

    public User getCurrentUser(String username) {
        return userService.findUserByUsername(username);
    }

    public List<ChatMessage> getChatMessages(Long chatId) {
        return chatMessageRepository.findAllByChatIdOrderByCreatedDate(chatId);
    }
}
