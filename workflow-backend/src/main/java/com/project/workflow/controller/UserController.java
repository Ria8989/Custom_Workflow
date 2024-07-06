package com.project.workflow.controller;

import com.project.workflow.entity.User;
import com.project.workflow.model.UserInput;
import com.project.workflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*
    @PostMapping( consumes="application/json", produces="application/json")
    public ResponseEntity<User> saveUser(@RequestBody UserInput user){
        System.out.println("UserInput : "+user);
        return null;
    }

     */

    @PostMapping( consumes="application/json", produces="application/json")
    public ResponseEntity<User> saveUser(@RequestBody UserInput user){
        try {
            return ResponseEntity.of(Optional.of(userService.createUser(user)));
        }catch (Exception e){
            System.out.println("Error saving user : "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping()
    public ResponseEntity<User> getUserByEmail(@RequestParam String email){
        try {
            return ResponseEntity.of(Optional.of(userService.readUserByEmail(email)));
        }catch (Exception e){
            System.out.println("Error getting user by email : "+e);
            return ResponseEntity.status(500).build();
        }

    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser(){
        try {
            return ResponseEntity.of(Optional.of(userService.readAllUsers()));
        }catch (Exception e){
            System.out.println("Error getting all user : "+e);
            return ResponseEntity.status(500).build();
        }
    }

}

