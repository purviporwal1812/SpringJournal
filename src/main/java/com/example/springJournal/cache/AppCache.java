package com.example.springJournal.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springJournal.entity.ConfigJournalAppEntity;
import com.example.springJournal.repository.ConfigJournalAppRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API
    }
    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;
    public Map<String , String> appCache = new HashMap<>();

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all =configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all){
            appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }
}
