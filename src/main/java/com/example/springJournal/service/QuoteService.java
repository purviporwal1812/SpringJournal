package com.example.springJournal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.springJournal.apiResponse.QuoteResponse;
@Component
public class QuoteService {
    private static final String API = "https://stoic.tekloon.net/stoic-quote";

    @Autowired
    private RestTemplate restTemplate;


    public QuoteResponse getQuote(){
        ResponseEntity<QuoteResponse> res = restTemplate.exchange(API, HttpMethod.GET , null ,QuoteResponse.class );
        QuoteResponse body = res.getBody();
        return body;

    }
}
