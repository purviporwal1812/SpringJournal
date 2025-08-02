package com.example.springJournal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.springJournal.apiResponse.WeatherResponse;


@Component
public class WeatherService {
    private static final String apiKey="3b9cd489ed06e3296b3cc80e0b5343e8";
    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;


    public WeatherResponse getWeather(String city){
        String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKey);
        ResponseEntity<WeatherResponse> res = restTemplate.exchange(finalAPI, HttpMethod.GET , null ,WeatherResponse.class );
        WeatherResponse body = res.getBody();
        return body;
    }

}
