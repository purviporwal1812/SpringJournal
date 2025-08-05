package com.example.springJournal.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.example.springJournal.entity.JournalEntry;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummarizationServiceTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SummarizationService service;

    @Test
    void shouldReturnGeneratedSummary() {
        // Set up the service configuration using reflection
        ReflectionTestUtils.setField(service, "apiUrl", "https://router.huggingface.co/hf-inference/models/facebook/bart-large-cnn");
        ReflectionTestUtils.setField(service, "apiToken", "hf_test_token");
        
        // Create test journal entries
        JournalEntry entry1 = new JournalEntry("First Entry");
        entry1.setContent("Today was a good day");
        
        JournalEntry entry2 = new JournalEntry("Second Entry");
        entry2.setContent("I learned something new");
        
        List<JournalEntry> entries = List.of(entry1, entry2);

        // Create mock response that matches what the API would return
        Map<String, Object> mockResponseBody = Map.of(
            "choices", List.of(
                Map.of("summary_text", "Cool summary!")
            )
        );

        ResponseEntity<Map> mockResponse = new ResponseEntity<>(mockResponseBody, HttpStatus.OK);
        
        // Mock the RestTemplate call with the correct parameter types
        when(restTemplate.exchange(
            anyString(),           // URL
            eq(HttpMethod.POST),   // HTTP method
            any(HttpEntity.class), // HTTP entity (body + headers)
            eq(Map.class)          // Response type
        )).thenReturn(mockResponse);

        // Test the service
        String summary = service.summarizeEntries(entries);
        
        assertThat(summary).isEqualTo("Cool summary!");
    }
}
