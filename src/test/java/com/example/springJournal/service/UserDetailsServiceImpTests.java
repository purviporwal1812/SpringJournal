package com.example.springJournal.service;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import static org.mockito.Mockito.*;

import com.example.springJournal.repository.UserRepo;

public class UserDetailsServiceImpTests {
    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;
    @Mock
    private UserRepo userRepo;
    void loadUserByUsernameTest(){
        
        UserDetails user = userDetailsServiceImp.loadUserByUsername("tia");


    }
}
