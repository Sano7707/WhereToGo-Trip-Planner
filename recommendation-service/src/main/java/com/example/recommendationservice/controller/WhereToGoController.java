package com.example.recommendationservice.controller;

import com.example.recommendationservice.dto.PlaceRequestDTO;
import com.example.recommendationservice.service.PlaceRecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/where-to-go")
public class WhereToGoController {

    private final PlaceRecommendationService recommendationService;

    public WhereToGoController(PlaceRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }
    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> getPlaces(@RequestBody PlaceRequestDTO request) {
        return recommendationService.getRecommendedPlaces(request)
            .map(ResponseEntity::ok) // Wrap the list in a ResponseEntity with HTTP 200 status
            .onErrorResume(e -> {
                // Handle errors and return an appropriate response
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            });
    }
}
