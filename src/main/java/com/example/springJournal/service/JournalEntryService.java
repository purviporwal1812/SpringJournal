package com.example.springJournal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springJournal.entity.JournalEntry;
import com.example.springJournal.entity.User;
import com.example.springJournal.repository.JournalEntryRepo;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try{
        User user = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
        } catch(Exception e){
            logger.error("error occured" , e);
            throw new RuntimeException("error" , e);
        }
        
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepo.findById(id);
    }
    @Transactional
    public void deleteById(ObjectId id, String username){
        try{
 User user = userService.findByUsername(username);
        boolean removed = user.getJournalEntries().removeIf(x->x.getId().equals(id));
        if(removed){
 userService.saveEntry(user);
        journalEntryRepo.deleteById(id);
        }
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("errror while deleteing" , e);

        }
       
       
    }

}
