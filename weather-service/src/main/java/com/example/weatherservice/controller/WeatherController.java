package com.example.weatherservice.controller;


import com.example.weatherservice.dto.WeatherResponseDTO;
import com.example.weatherservice.service.WeatherService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/api/weather")
    public Mono<WeatherResponseDTO> getWeather(@RequestParam String city) {
        return weatherService.getWeatherForCity(city);
    }
}