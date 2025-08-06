package com.example.springJournal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springJournal.entity.User;
import com.example.springJournal.service.UserService;
import com.example.springJournal.utils.JwtUtil;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired 
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @GetMapping
    public String health(){
        return "Hello";
    }
    @PostMapping("/signup")
    public boolean signup(@RequestBody User user){
        userService.saveNewUser(user);
        return true;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
return new ResponseEntity<>(jwt,HttpStatus.OK);

            
        } catch (Exception e) {
            return new ResponseEntity<>("Incoreect username or password " , HttpStatus.BAD_REQUEST);
        }

    }
}
