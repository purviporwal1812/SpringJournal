package com.example.springJournal.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.springJournal.entity.JournalEntry;

public interface JournalEntryRepo extends MongoRepository<JournalEntry , ObjectId> {
    List<JournalEntry> findByIdInAndDateBetween(List<ObjectId> ids, LocalDateTime start, LocalDateTime end);

    
}