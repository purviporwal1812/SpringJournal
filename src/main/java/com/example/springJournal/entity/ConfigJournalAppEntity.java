package com.example.springJournal.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "config_journal_app")
@Data
public class ConfigJournalAppEntity {
    private String key;
    private String value;
}
