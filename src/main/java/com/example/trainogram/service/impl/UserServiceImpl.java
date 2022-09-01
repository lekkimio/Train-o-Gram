package com.example.trainogram.service.impl;

import com.example.trainogram.exception.UserException;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
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
    public User addUser(User user) throws UserException {
        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new UserException("User already exists");
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
    public User updateUser(User user) throws UserException {
        //TODO:
        // check if user exists
        // check if user is authenticated and if he is trying to update his own account, if not throw exception,
        // if yes, update user
        // check if input info is the same as the one in the database
        if (userRepository.findById(user.getId()).isPresent()) {
            if (!findAuthenticatedUser().getId().equals(user.getId()) || findAuthenticatedUser().getRole().equals(Role.ADMIN)) {
                return userRepository.save(user);
            }else {
                throw new UserException("You have no authorities");
            }
        } else {
            throw new UserException("User not found");
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) throws UserException {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public User findAuthenticatedUser() {
        return userRepository.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
