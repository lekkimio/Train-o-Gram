package com.example.trainogram.service;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.model.User;

public interface DeletingService {


    void deleteUser(User deletedUser) throws Status434UserNotFound;
}
