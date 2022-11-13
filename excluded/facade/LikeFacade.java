//package com.example.trainogram.facade;
//
//import com.example.trainogram.exception.CustomException;
//import com.example.trainogram.model.Post;
//import com.example.trainogram.model.User;
//import com.example.trainogram.model.dto.response.PostResponseDto;
//import com.example.trainogram.model.dto.response.UserResponseDto;
//
//import java.util.List;
//
//public interface LikeFacade {
//    List<User> findAllLikedUsersToComment(Long commentId);
//
//    List<User> findAllLikedUsersToPost(Long postId);
//
//    List<Post> findAllLikedPostsByUser();
//
//
//    void addLikeToPost(Long postId) throws CustomException;
//
//    void deleteLikeFromPost(Long postId);
//
//    void addLikeToComment(Long commentId) throws CustomException;
//
//    void deleteLikeFromComment(Long commentId);
//}
