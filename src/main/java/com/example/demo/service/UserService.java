package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException; 
import org.springframework.http.HttpStatus;           

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        String cleanEmail = email != null ? email.toLowerCase().trim() : "";
        return userRepository.findByEmail(cleanEmail)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User[] getAllUsers() {
        return userRepository.findAll().toArray(new User[0]);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authenticated user found");
        }
        String email = authentication.getName();
        return findByEmail(email);
    }

    @Transactional
    public User registerUser(User user) {
        String cleanEmail = user.getEmail().toLowerCase().trim();
        if (userRepository.findByEmail(cleanEmail).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMAIL ALREADY IN USE");
        }

        user.setEmail(cleanEmail);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User userDetails) {
        User user = getCurrentUser();

        if (userDetails.getName() != null) user.setName(userDetails.getName());
        if (userDetails.getLastName() != null) user.setLastName(userDetails.getLastName());

        if (userDetails.getEmail() != null) {
            String cleanNewEmail = userDetails.getEmail().toLowerCase().trim();

            if (!cleanNewEmail.equals(user.getEmail())) {
                if (userRepository.findByEmail(cleanNewEmail).isPresent()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMAIL ALREADY IN USE");
                }
                user.setEmail(cleanNewEmail);
            }
        }

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser() {
        User user = getCurrentUser();
        userRepository.delete(user);
    }
}