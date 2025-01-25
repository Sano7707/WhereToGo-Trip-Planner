package com.example.transportationsservice.controller;


import com.example.transportationsservice.entity.Transportation;
import com.example.transportationsservice.service.TransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transportations")
public class TransportationController {

    @Autowired
    private TransportationService transportationService;

    @GetMapping
    public List<Transportation> getAllTransportations() {
        return transportationService.getAllTransportations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transportation> getTransportationById(@PathVariable Long id) {
        Optional<Transportation> transportation = transportationService.getTransportationById(id);
        return transportation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Transportation>> searchTransportations(@RequestParam String city) {
        if (city == null || city.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Transportation> transportations = transportationService.searchTransportations(city);
        return ResponseEntity.ok(transportations);
    }

    @PostMapping
    public Transportation addTransportation(@RequestBody Transportation transportation) {
        return transportationService.addTransportation(transportation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transportation> updateTransportation(@PathVariable Long id, @RequestBody Transportation updatedTransportation) {
        Optional<Transportation> transportation = transportationService.updateTransportation(id, updatedTransportation);
        return transportation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransportation(@PathVariable Long id) {
        boolean isDeleted = transportationService.deleteTransportation(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}