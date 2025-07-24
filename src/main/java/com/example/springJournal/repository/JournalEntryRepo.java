package com.example.springJournal.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.springJournal.entity.JournalEntry;

public interface JournalEntryRepo extends MongoRepository<JournalEntry , ObjectId> {
}