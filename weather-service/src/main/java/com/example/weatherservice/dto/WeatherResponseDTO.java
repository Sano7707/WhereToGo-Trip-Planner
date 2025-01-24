package com.example.weatherservice.dto;

import java.util.List;


public class WeatherResponseDTO {
    private String city;
    private String temperature;
    private String condition;
    private String humidity;
    private List<DailyForecast> forecast; // 3-day forecast

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public List<DailyForecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<DailyForecast> forecast) {
        this.forecast = forecast;
    }

    public WeatherResponseDTO(String city, String temperature, String condition, String humidity, List<DailyForecast> forecast) {
        this.city = city;
        this.temperature = temperature;
        this.condition = condition;
        this.humidity = humidity;
        this.forecast = forecast;
    }
}


