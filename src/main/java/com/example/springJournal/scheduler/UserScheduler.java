package com.example.springJournal.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.springJournal.cache.AppCache;
import com.example.springJournal.entity.JournalEntry;
import com.example.springJournal.entity.User;
import com.example.springJournal.enums.Sentiment;
import com.example.springJournal.repository.JournalEntryRepo;
import com.example.springJournal.repository.UserRepoImpl;
import com.example.springJournal.service.EmailService;
import com.example.springJournal.service.SummarizationService;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserRepoImpl userRepoImpl;
    @Autowired
    private AppCache appCache;
    @Autowired
    private SummarizationService summarizationService;

    @Scheduled(cron = "0 */10 * * * *")
    public void fetchUsersAndSendSAMail(){
       List<User> users = userRepoImpl.getUsersForSA();

        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(10, ChronoUnit.MINUTES)))
                .map(x -> x.getSentiment())
                .collect(Collectors.toList());
            
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            
            if (mostFrequentSentiment != null) {                                 
                emailService.sendEmail(user.getEmail(), "Sentiment for previous 10 minutes", mostFrequentSentiment.toString());                         
            }
        }
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void clearAppCache(){
        appCache.init();
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void sendDigest() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minus(10, ChronoUnit.MINUTES);

        for (User user : userRepoImpl.getUsersForSA()) {
            List<ObjectId> ids = user.getJournalEntries()
                         .stream()
                         .map(JournalEntry::getId)
                         .collect(Collectors.toList());
                         
            List<JournalEntry> entries = journalEntryRepo.findByIdInAndDateBetween(ids, start, now);
            if (entries.isEmpty()) continue;

            String summary = summarizationService.summarizeEntries(entries);
            emailService.sendSummaryEmail(user.getEmail(), summary, false);
        }
    }
}