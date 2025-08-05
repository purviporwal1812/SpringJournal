package com.example.springJournal.service;

import com.example.springJournal.entity.JournalEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummarizationService {
    private final RestTemplate restTemplate;

    @Value("${summarization.api.url}")
    private String apiUrl;
    @Value("${summarization.api.token}")
    private String apiToken;

    public String summarizeEntries(List<JournalEntry> entries) {
        // Concatenate the text of all entries with separators
        String combined = entries.stream()
            .map(JournalEntry::getContent)           // or your field name
            .collect(Collectors.joining("\n\n---\n\n"));

        // Build HF inference request
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = Map.of(
            "inputs", combined,
            "parameters", Map.of("max_length", 150),
            "options", Map.of("use_gpu", false)
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> resp = restTemplate.exchange(
            apiUrl, HttpMethod.POST, request, Map.class);

        // HF returns a List of summaries under “generated_text”
        // e.g. [ { "summary_text": "A short summary…"} ]
        if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
            Object summary = ((List) resp.getBody().get("choices"))
                               .get(0);
            return ((Map<?,?>) summary).get("summary_text").toString();
        }
        // fallback
        return "Sorry, I couldn’t generate a summary this time.";
    }
}
