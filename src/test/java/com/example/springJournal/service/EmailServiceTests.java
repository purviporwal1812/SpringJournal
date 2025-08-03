package com.example.springJournal.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;
    @Test
    void testSendMail(){
        emailService.sendEmail("purviporwal1812@gmail.com", "testing", "hi from jouranl app");
    }

}
