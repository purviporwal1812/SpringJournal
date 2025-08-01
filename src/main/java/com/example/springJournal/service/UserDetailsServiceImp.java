package com.example.springJournal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springJournal.repository.UserRepo;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.springJournal.entity.User user = userRepo.findByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("username not found : " + username);
        }
        
        String[] roles = user.getRoles() != null ? 
            user.getRoles().toArray(new String[0]) : 
            new String[]{"USER"};
        
        return User.withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(roles)  
            .build();
    }
}