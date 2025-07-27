package com.example.springJournal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.springJournal.entity.User;
import com.example.springJournal.repository.UserRepo;
import com.example.springJournal.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @PutMapping
public ResponseEntity<?> updateUser(@RequestBody User user) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User userInDb = userService.findByUsername(username);

    if (userInDb == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    userInDb.setUsername(user.getUsername());

    String encodedPassword = passwordEncoder.encode(user.getPassword());
    userInDb.setPassword(encodedPassword);

    userService.saveEntry(userInDb);

    return ResponseEntity.noContent().build();
}
       
    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
