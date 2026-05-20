package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UpdateUserRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PutMapping("/update")
    public ResponseEntity<User> testUpdate(@RequestBody UpdateUserRequest updateRequest) {
       
        User userDetails = new User();
        userDetails.setName(updateRequest.getName());
        userDetails.setLastName(updateRequest.getLastName());
        userDetails.setEmail(updateRequest.getEmail());
        userDetails.setPassword(updateRequest.getPassword());

        User updatedUser = userService.updateUser(userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<User[]> getAllUsers() {
        User[] users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}