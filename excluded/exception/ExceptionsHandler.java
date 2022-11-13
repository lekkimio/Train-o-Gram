//package com.example.trainogram.exception;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//
//@ControllerAdvice
//public class ExceptionsHandler {
//
//    @ExceptionHandler(value = {CustomException.class})
//    public ResponseEntity<Object> handlerException(CustomException e){
//
//        return new ResponseEntity<>(ApiException.builder()
//                .message(e.getMessage())
//                .status(e.status())
//                .code(e.status().value())
//                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
//                .build(), e.status());
//    }
//
//}
