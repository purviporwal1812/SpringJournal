package com.example.springJournal.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
        User user = userService.findByUsername(username);
        List<JournalEntry> all =user.getJournalEntries();
        if(all!=null && !all.isEmpty())return new ResponseEntity<>(all , HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public boolean createEntry(@RequestBody JournalEntry myEntry,@PathVariable String username){
        journalEntryService.saveEntry(myEntry,username);
        return true;
    } 
    @GetMapping("id/{myId}")
    public JournalEntry getById(@PathVariable ObjectId myId){
        return journalEntryService.findById(myId).orElse(null);
    }
    @DeleteMapping("id/{username}/{myId}")
    public boolean deleteById(@PathVariable ObjectId myId , @PathVariable String username){
        journalEntryService.deleteById(myId,username);
        return true;
    }
     @PutMapping("id/{username}/{myId}")
    public JournalEntry updateById(@PathVariable ObjectId myId , @RequestBody JournalEntry myEntry , @PathVariable String username){
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old!=null){
            if (myEntry.getTitle() != null && !myEntry.getTitle().isEmpty()) {
                old.setTitle(myEntry.getTitle());
            }
            if (myEntry.getContent() != null && !myEntry.getContent().isEmpty()) {
                old.setContent(myEntry.getContent());
            }
            journalEntryService.saveEntry(old);
        }
        return old;
    }
}
