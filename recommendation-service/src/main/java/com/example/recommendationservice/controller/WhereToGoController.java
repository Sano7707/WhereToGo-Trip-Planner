package com.example.recommendationservice.controller;

import com.example.recommendationservice.dto.PlaceRequestDTO;
import com.example.recommendationservice.service.PlaceRecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public Mono<ResponseEntity<Map<String, Object>>> getPlaces(
        @RequestBody PlaceRequestDTO request,
        @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        return recommendationService.getRecommendedPlaces(request, authHeader)
            .map(ResponseEntity::ok)
            .onErrorResume(e -> {
                if (e instanceof SecurityException) {
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                }
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            });
    }
}