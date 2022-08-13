package com.example.trainogram.service.impl;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<User> addUser(User user) throws UserNotFoundException {
        Optional<User> user1 = Optional.ofNullable(userRepository.findUserByUsername(user.getUsername()));
        if (user1.isPresent()) {
            throw new UserNotFoundException("User already exists");
        } else {
            String encodedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPass);
            userRepository.save(user);
        }
        return user1;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, User user) throws UserNotFoundException {

        if (userRepository.findById(id).isPresent()) {
            user.setId(id);
            String encodedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPass);
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User findAuthenticatedUser() {
        return userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
