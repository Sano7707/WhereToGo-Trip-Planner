package com.example.tripplanningservice.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TripPlannerService {

    private final WebClient flightClient = WebClient.create("http://flight-service:8087/api");
    private final WebClient hotelClient = WebClient.create("http://hotel-service:8088/api");
    private final WebClient transportationClient = WebClient.create("http://transportation-service:8089/api");

    public Mono<Map<String, Object>> aggregateResults(String departure, String destination) {
        // Fetch flights, hotels, and transportations in parallel
        Mono<List<Map<String, Object>>> flightsMono = flightClient.get()
            .uri(uriBuilder -> uriBuilder.path("/flights/search")
                .queryParam("departure", departure)
                .queryParam("destination", destination)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {});

        Mono<List<Map<String, Object>>> hotelsMono = hotelClient.get()
            .uri(uriBuilder -> uriBuilder.path("/hotels/search")
                .queryParam("city", destination)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {});

        Mono<List<Map<String, Object>>> transportationsMono = transportationClient.get()
            .uri(uriBuilder -> uriBuilder.path("/transportations/search")
                .queryParam("city", destination)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {});

        // Combine results and append cheapest options/total cost
        return Mono.zip(flightsMono, hotelsMono, transportationsMono)
            .map(tuple -> {
                List<Map<String, Object>> flights = tuple.getT1();
                List<Map<String, Object>> hotels = tuple.getT2();
                List<Map<String, Object>> transportations = tuple.getT3();

                // Build original response with lists
                Map<String, Object> response = new HashMap<>();
                response.put("flights", flights);
                response.put("hotels", hotels);
                response.put("transportations", transportations);

                System.out.println("Transportations " + transportations);
                // Find and append cheapest options
                Map<String, Object> cheapestFlight = flights.stream()
                    .min(Comparator.comparingDouble(f -> (Double) f.get("cost")))
                    .orElseThrow(() -> new RuntimeException("No flights found"));
                response.put("cheapestFlight", cheapestFlight);

                Map<String, Object> cheapestHotel = hotels.stream()
                    .min(Comparator.comparingDouble(h -> (Double) h.get("pricePerNight")))
                    .orElseThrow(() -> new RuntimeException("No hotels found"));
                response.put("cheapestHotel", cheapestHotel);

                Map<String, Object> cheapestTransportation = transportations.stream()
                    .min(Comparator.comparingDouble(t -> (Double) t.get("cost")))
                    .orElseThrow(() -> new RuntimeException("No transportations found"));
                response.put("cheapestTransportation", cheapestTransportation);

                // Calculate and append total cost
                double totalCost = (Double) cheapestFlight.get("cost")
                    + (Double) cheapestHotel.get("pricePerNight")
                    + (Double) cheapestTransportation.get("cost");
                response.put("totalCost", totalCost);

                return response;
            });
    }
}