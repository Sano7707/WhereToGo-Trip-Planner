package com.example.weatherservice.service;


import com.example.weatherservice.dto.WeatherResponseDTO;
import com.example.weatherservice.dto.DailyForecast;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class WeatherService {

    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.weatherapi.com/v1").build();
    }

    public Mono<WeatherResponseDTO> getWeatherForCity(String city) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/forecast.json")
                .queryParam("key", "09832a18af4043b1894164017252401") // Replace with your API key
                .queryParam("q", city)
                .queryParam("days", 5)
                .queryParam("aqi","no")
                .queryParam("alerts","no")// Fetch weather for the next 3 days
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .map(response -> {
                Map<String, Object> location = (Map<String, Object>) response.get("location");
                Map<String, Object> current = (Map<String, Object>) response.get("current");
                List<Map<String, Object>> forecastDays = (List<Map<String, Object>>) ((Map<String, Object>) response.get("forecast")).get("forecastday");

                // Extract 3-day forecast
                List<DailyForecast> forecast = forecastDays.stream()
                    .map(day -> {
                        Map<String, Object> dayData = (Map<String, Object>) day.get("day");
                        return new DailyForecast(
                            (String) day.get("date"),
                            dayData.get("maxtemp_c") + "°C",
                            dayData.get("mintemp_c") + "°C",
                            "N/A" // Condition is not available in the provided response
                        );
                    })
                    .collect(Collectors.toList());

                return new WeatherResponseDTO(
                    (String) location.get("name"),
                    current.get("temp_c") + "°C",
                    "N/A", // Condition is not available in the provided response
                    "N/A", // Humidity is not available in the provided response
                    forecast
                );
            });
    }
}