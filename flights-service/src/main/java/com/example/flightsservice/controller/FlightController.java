package com.example.flightsservice.controller;


import com.example.flightsservice.entity.Flight;
import com.example.flightsservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping("/search")
    public ResponseEntity<List<Flight>> searchFlights(
        @RequestParam String departure,
        @RequestParam String destination) {

        if (departure == null || destination == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Flight> flights = flightService.searchFlights(departure, destination);
        return ResponseEntity.ok(flights);
    }
}