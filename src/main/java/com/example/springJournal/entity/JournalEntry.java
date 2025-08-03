package com.example.springJournal.entity;

import org.bson.types.ObjectId;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.springJournal.enums.Sentiment;
import com.mongodb.lang.NonNull;

import java.time.LocalDateTime;

@Document(collection = "journalEntry")
@Data
public class JournalEntry {
    
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;
   
}
