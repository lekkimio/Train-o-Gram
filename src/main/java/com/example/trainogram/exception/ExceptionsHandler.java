package com.example.trainogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handlerException(CustomException e){

        return new ResponseEntity<>(ApiException.builder()
                .message(e.getMessage())
                .status(e.status())
                .code(e.status().value())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build(), e.status());
    }

   /* @ExceptionHandler(value = {UserException.class})
    public ResponseEntity<Object> handlersException(UserException e){


        return new ResponseEntity<>(ApiException.builder()
                .message(e.getMessage())
                .httpStatus(status)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build(), status);
    }

    @ExceptionHandler(value = {CommentException.class})
    public ResponseEntity<Object> handlerException(CommentException e){
        return new ResponseEntity<>(ApiException.builder()
                .message(e.getMessage())
                .httpStatus(status)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build(), status);
    }

    @ExceptionHandler(value = {LikeException.class})
    public ResponseEntity<Object> handlerException(LikeException e){
        return new ResponseEntity<>(ApiException.builder()
                .message(e.getMessage())
                .httpStatus(status)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build(), status);
    }

    @ExceptionHandler(value = {PostException.class})
    public ResponseEntity<Object> handlerException(PostException e){
        return new ResponseEntity<>(ApiException.builder()
                .message(e.getMessage())
                .httpStatus(status)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build(), status);
    }

    @ExceptionHandler(value = {NotificationException.class})
    public ResponseEntity<Object> handlerException(NotificationException e){
        return new ResponseEntity<>(ApiException.builder()
                .message(e.getMessage())
                .httpStatus(status)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build(), status);
    }

    @ExceptionHandler(value = {FriendshipException.class})
    public ResponseEntity<Object> handlerException(FriendshipException e){
        return new ResponseEntity<>(ApiException.builder()
                .message(e.getMessage())
                .httpStatus(status)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build(), status);
    }*/

}
