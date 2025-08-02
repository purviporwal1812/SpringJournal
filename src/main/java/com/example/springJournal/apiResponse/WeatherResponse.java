package com.example.springJournal.apiResponse;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

@Getter
@Setter
public class WeatherResponse{
    private Current current;


    @Getter
    @Setter
public class Current{
    @JsonProperty("observation_time")
    private String observationTime;
    private int temperature;
    @JsonProperty("weather_icons")
    private ArrayList<String> weather_icons;
    @JsonProperty("weather_descriptions")
    private ArrayList<String> weatherDescriptions;

    private int pressure;
    private int precip;
    private int humidity;
    private int cloudcover;
    private int feelslike;
    @JsonProperty("is_day")
    private String isDay;
}



}

