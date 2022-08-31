package com.example.trainogram.service.impl;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.UserDto;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.UserService;
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
    public User addUser(User user) throws UserNotFoundException {
        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new UserNotFoundException("User already exists");
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
    public User updateUser(Long id, User user) throws UserNotFoundException {
        //TODO: check if user is authenticated and if he is trying to update his own account, if not throw exception,
        // if yes, update user
        // check if user exists
        // check if input info is the same as the one in the database
        if (userRepository.findById(id).isPresent()) {
           user.setRole(Role.USER);
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
        return userRepository.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
