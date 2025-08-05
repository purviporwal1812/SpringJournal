package com.example.springJournal.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import com.example.springJournal.apiResponse.WeatherResponse;
import com.example.springJournal.cache.AppCache;

@Service
public class WeatherService {

    @Autowired
    private AppCache appCache;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        try {
            String template = appCache.appCache.get(AppCache.keys.WEATHER_API.toString());
            
            // Check if template exists
            if (template == null || template.isEmpty()) {
                throw new RuntimeException("Weather API template not found in cache");
            }
            
            // URL-encode the city name
            String cityEncoded = URLEncoder.encode(city, StandardCharsets.UTF_8);
            
            // Create the URL
            String url = String.format(template, apiKey, cityEncoded);
            
            // Make the API call
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
            
            // Validate the response
            if (response == null) {
                throw new RuntimeException("Weather API returned null response");
            }
            
            return response;
            
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error processing weather request: " + e.getMessage(), e);
        }
    }
}
