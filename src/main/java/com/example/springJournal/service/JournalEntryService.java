package com.example.springJournal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.springJournal.entity.JournalEntry;
import com.example.springJournal.entity.User;
import com.example.springJournal.repository.JournalEntryRepo;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        User user = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
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
