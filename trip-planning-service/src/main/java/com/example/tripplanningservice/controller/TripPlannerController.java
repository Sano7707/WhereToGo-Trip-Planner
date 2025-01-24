package com.example.tripplanningservice.controller;

import com.example.tripplanningservice.service.TripPlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin
    @GetMapping("/search")
    public Mono<Map<String, Object>> searchAllServices(
        @RequestParam String departure,
        @RequestParam String destination
    ) {
        return tripPlannerService.aggregateResults(departure, destination);
    }
}
