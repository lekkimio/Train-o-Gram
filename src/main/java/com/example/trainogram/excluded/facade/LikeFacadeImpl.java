//package com.example.trainogram.facade;
//
//import com.example.trainogram.exception.CustomException;
//import com.example.trainogram.model.Comment;
//import com.example.trainogram.model.Post;
//import com.example.trainogram.model.User;
//import com.example.trainogram.service.*;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//
//public class LikeFacadeImpl implements LikeFacade {
//
//
//    private final LikeToCommentService likeToCommentService;
//    private final LikeToPostService likeToPostService;
//    private final UserService userService;
//    private final PostService postService;
//    private final CommentService commentService;
//    private final NotificationService notificationService;
//
//    @Autowired
//    public LikeFacadeImpl(LikeToCommentService likeToCommentService, LikeToPostService likeToPostService,
//                          UserService userService, @Lazy PostService postService, CommentService commentService,
//                          NotificationService notificationService) {
//        this.likeToCommentService = likeToCommentService;
//        this.likeToPostService = likeToPostService;
//        this.userService = userService;
//        this.postService = postService;
//        this.commentService = commentService;
//        this.notificationService = notificationService;
//
//    }
//
//
//    @Override
//    public List<Post> findAllLikedPostsByUser() {
//        User user = userService.findAuthenticatedUser();
//        return likeToPostService.findAllLikedPostsByUser(user);
//
//    }
//
//    @Override
//    public List<User> findAllLikedUsersToPost(Long postId) {
//        return likeToPostService.findAllLikedUsersToPost(postId);
//    }
//
//    @Override
//    public List<User> findAllLikedUsersToComment(Long commentId) {
//        return likeToCommentService.findAllLikedUsersToComment(commentId);
//    }
//
//    @Override
//    public void addLikeToPost(Long postId) throws CustomException {
//         Post post = postService.findPostById(postId);
//         User user = userService.findAuthenticatedUser();
//         likeToPostService.addLikeToPost(post, user);
//            post.setLikes(post.getLikes()+1);
//            postService.updatePostLikeCount(post);
//            notificationService.sendNotification(post.getPostAuthor(), "User "+user.getUsername()+" liked your post");
//    }
//
//    @Override
//    public void deleteLikeFromPost(Long postId) {
//        Post post = postService.findPostById(postId);
//        User user = userService.findAuthenticatedUser();
//        likeToPostService.deleteLikeFromPost(post, user);
//        int likeCount = post.getLikes();
//        if (!(likeCount-1 < 0)) {
//            post.setLikes(post.getLikes()-1);
//        }
//        postService.updatePostLikeCount(post);
//    }
//
//    @Override
//    public void addLikeToComment(Long commentId) throws CustomException {
//        Comment comment = commentService.findCommentById(commentId);
//        User user = userService.findAuthenticatedUser();
//        likeToCommentService.addLikeToComment(comment, user);
//        comment.setLikes(comment.getLikes()+1);
//        commentService.updateCommentLikeCount(comment);
//
//        notificationService.sendNotification(comment.getCommentAuthor(), "User "+user.getUsername()+" liked your comment");
//    }
//
//    @Override
//    public void deleteLikeFromComment(Long commentId) {
//        Comment comment = commentService.findCommentById(commentId);
//        User user = userService.findAuthenticatedUser();
//        likeToCommentService.deleteLikeFromComment(comment, user);
//        int likeCount = comment.getLikes();
//        if (!(likeCount-1 < 0)) {
//            comment.setLikes(comment.getLikes()-1);
//        }
//        commentService.updateCommentLikeCount(comment);
//
//    }
//}
