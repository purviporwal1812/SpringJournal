package com.example.springJournal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springJournal.repository.UserRepo;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepo userRepo;
    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
        "tia",
        "admin123",
        "purvi"
    })
    public void testfindByUsername(String username){
        assertNotNull(userRepo.findByUsername(username));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
        "1,1,2",
        "2,4,7",
        "6,8,14"
    })
    public void test(int a , int b , int expected){
        assertEquals(expected , a+b);
    }

}
