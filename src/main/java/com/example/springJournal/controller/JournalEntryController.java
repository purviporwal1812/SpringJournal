package com.example.springJournal.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springJournal.entity.JournalEntry;
import com.example.springJournal.entity.User;
import com.example.springJournal.service.JournalEntryService;
import com.example.springJournal.service.UserService;

@RestController
@RequestMapping("/journal")

public class JournalEntryController {


    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

     @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<JournalEntry> all = journalEntryService.findByUsername(username);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
        journalEntryService.saveEntry(myEntry,username);
        return true;
    } 
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myId){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.findByUsername(username);
    List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
    if(!collect.isEmpty()){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
        }
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{myId}")
    public boolean deleteById(@PathVariable ObjectId myId ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.deleteById(myId,username);
        return true;
    }
    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId myId , @RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
    List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
    if(!collect.isEmpty()){
         Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            JournalEntry old = journalEntry.get();
             if (myEntry.getTitle() != null && !myEntry.getTitle().isEmpty()) {
                old.setTitle(myEntry.getTitle());
            }
            if (myEntry.getContent() != null && !myEntry.getContent().isEmpty()) {
                old.setContent(myEntry.getContent());
            }
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
