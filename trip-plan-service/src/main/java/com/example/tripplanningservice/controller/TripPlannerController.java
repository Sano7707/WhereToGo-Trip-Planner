package com.example.tripplanningservice.controller;

import com.example.tripplanningservice.service.TripPlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TripPlannerController {
    @Autowired
    private TripPlannerService tripPlannerService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/search")
    public Mono<ResponseEntity<Map<String, Object>>> searchAllServices(
        @RequestParam String departure,
        @RequestParam String destination,
        @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.error(new SecurityException("Authorization header is required"));
        }
        return tripPlannerService.aggregateResults(departure, destination, authHeader).map(ResponseEntity::ok)
            .onErrorResume(e -> {
                if (e instanceof SecurityException) {
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                }
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            });
    }
}
