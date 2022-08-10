package com.example.trainogram.service;

import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;

public interface CommentService {

    void addComment(User user, Post post, String text);

    void deleteComment(User user, Post post, Comment comment);

}
