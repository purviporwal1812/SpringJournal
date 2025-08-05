package com.example.springJournal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to , String subject , String body){
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
        } catch (Exception e) {
            System.out.println("error sending mail: " +e);
        }

    }
       public void sendSummaryEmail(String to, String summary, boolean isMonthly) {
        String subject = isMonthly ? "Your Monthly Journal Digest" : "Your Weekly Journal Digest";
        String header  = isMonthly 
            ? "Here’s what you reflected on this month…" 
            : "Here’s what you talked about this week…";

        String html = "<p>Hi! We’ve summarized your " 
            + (isMonthly ? "last month’s" : "last week’s") 
            + " entries:</p>"
            + "<blockquote>" + summary + "</blockquote>"
            + "<p>Keep writing,</p><p>The SpringJournal Team</p>";

        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(header + "\n\n" + summary, html);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            System.out.println("error is:" + e);
        }
    }

}
