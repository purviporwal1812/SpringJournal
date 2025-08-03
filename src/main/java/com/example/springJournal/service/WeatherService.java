package com.example.springJournal.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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


    public WeatherResponse getWeather(String city){
         String template = appCache.appCache.get(AppCache.keys.WEATHER_API.toString());
    
    // 2) URL-encode the city name so spaces/special-characters are safe
    String cityEncoded = URLEncoder.encode(city, StandardCharsets.UTF_8);
    
    // 3) inject your API key and city into the template
    String url = String.format(template, apiKey, cityEncoded);

    // 4) make the call
    return restTemplate.getForObject(url, WeatherResponse.class);
    }

}
