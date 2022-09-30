package com.example.trainogram.service.impl;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User createUser(User user) throws CustomException {
        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new CustomException("User already exists", HttpStatus.CONFLICT, HttpStatus.CONFLICT.value());
        } else {
            String encodedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPass);
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user){
        userRepository.save(user);

//        if (userRepository.findById(user.getId()).isPresent()) {
//            if (findAuthenticatedUser().getId().equals(user.getId())) {
//                return userRepository.save(user);
//            }else {
//                throw new UserException("You have no authorities");
//            }
//        } else {
//            throw new UserException("User not found");
//        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) throws  CustomException {
        return userRepository.findById(id).orElseThrow(() -> new CustomException("User not found",HttpStatus.NOT_FOUND));
    }

    @Override
    public User findAuthenticatedUser() {
        return userRepository.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());

    }
}
