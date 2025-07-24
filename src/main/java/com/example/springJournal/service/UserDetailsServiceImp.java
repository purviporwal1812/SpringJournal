package com.example.springJournal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.springJournal.repository.UserRepo;

@Component
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Handle the case where repository returns User directly (not Optional<User>)
        com.example.springJournal.entity.User user = userRepo.findByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("username not found : " + username);
        }
        
        // Handle null roles safely
        String[] roles = user.getRoles() != null ? 
            user.getRoles().toArray(new String[0]) : 
            new String[]{"USER"};
        
        // Use the correct method name: .roles() not .userRoles()
        return User.withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(roles)  // Use the roles array we created above
            .build();
    }
}