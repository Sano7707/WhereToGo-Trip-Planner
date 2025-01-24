package com.example.recommendationservice.service;

import com.example.recommendationservice.dto.PlaceRequestDTO;
import com.example.recommendationservice.dto.PlaceResponseDTO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlaceRecommendationService {

    private final WebClient geminiClient = WebClient.create("http://localhost:8083/api");
    private final WebClient pexelsClient = WebClient.create("http://localhost:8084/api");
    private final WebClient weatherClient = WebClient.create("http://localhost:8085/api");

    public Mono<Map<String, Object>> getRecommendedPlaces(PlaceRequestDTO request) {
        // Fetch places and weather in parallel
        Mono<List<PlaceResponseDTO>> placesMono = fetchPlacesWithImages(request);
        Mono<Map<String, Object>> weatherMono = fetchWeatherData(request.getDestination());

        return Mono.zip(placesMono, weatherMono)
            .map(tuple -> {
                Map<String, Object> response = new HashMap<>();
                response.put("weather", tuple.getT2());
                response.put("places", tuple.getT1());
                return response;
            });
    }

    private Mono<List<PlaceResponseDTO>> fetchPlacesWithImages(PlaceRequestDTO request) {
        return geminiClient.get()
            .uri(uriBuilder -> uriBuilder.path("/gemini-response")
                .queryParam("destination", request.getDestination())
                .queryParam("budget", request.getBudget())
                .queryParam("preferences", request.getPreferences())
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
            .flatMapMany(Flux::fromIterable)
            .flatMap(place -> {
                String placeName = (String) place.get("place_name");

                return pexelsClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/image-link")
                        .queryParam("placeName", placeName)
                        .build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .onErrorResume(e -> Mono.just(Map.of("image_link", "")))
                    .map(imageResponse -> new PlaceResponseDTO(
                        placeName,
                        (String) place.get("description"),
                        (String) place.get("website_link"),
                        (String) imageResponse.get("image_link")
                    ));
            })
            .collectList();
    }

    private Mono<Map<String, Object>> fetchWeatherData(String city) {
        return weatherClient.get()
            .uri(uriBuilder -> uriBuilder.path("/weather")
                .queryParam("city", city)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .onErrorResume(e -> createFallbackWeather(city));
    }

    private Mono<Map<String, Object>> createFallbackWeather(String city) {
        return Mono.just(Map.of(
            "city", city,
            "temperature", "N/A",
            "condition", "N/A",
            "humidity", "N/A",
            "forecast", List.of()
        ));
    }
}