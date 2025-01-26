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
    private final WebClient authClient = WebClient.create("http://authentication-service:8082");

    public Mono<Map<String, Object>> aggregateResults(String departure, String destination, String authHeader) {
        return verifyToken(authHeader)
            .flatMap(isValid -> {
                if (!isValid) {
                    return Mono.error(new SecurityException("Invalid or expired token"));
                }

                // Proceed with data fetching if token is valid
                Mono<List<Map<String, Object>>> flightsMono = flightClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/flights/search")
                        .queryParam("departure", departure)
                        .queryParam("destination", destination)
                        .build())
                    .header("Authorization", authHeader)  // Forward token to downstream service
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {});

                Mono<List<Map<String, Object>>> hotelsMono = hotelClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/hotels/search")
                        .queryParam("city", destination)
                        .build())
                    .header("Authorization", authHeader)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {});

                Mono<List<Map<String, Object>>> transportationsMono = transportationClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/transportations/search")
                        .queryParam("city", destination)
                        .build())
                    .header("Authorization", authHeader)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {});

                return Mono.zip(flightsMono, hotelsMono, transportationsMono)
                    .map(tuple -> buildResponse(tuple.getT1(), tuple.getT2(), tuple.getT3()));
            });
    }

    private Mono<Boolean> verifyToken(String authHeader) {
        return authClient.post()
            .uri("/verify")
            .header("Authorization", authHeader)
            .retrieve()
            .onStatus(status -> status.isError(), response ->
                Mono.error(new SecurityException("Invalid token"))
            )
            .bodyToMono(Map.class)
            .map(response -> (Boolean) response.get("valid"))
            .onErrorResume(e -> Mono.just(false));
    }

    private Map<String, Object> buildResponse(List<Map<String, Object>> flights,
                                              List<Map<String, Object>> hotels,
                                              List<Map<String, Object>> transportations) {
        Map<String, Object> response = new HashMap<>();

        response.put("flights", flights);
        response.put("hotels", hotels);
        response.put("transportations", transportations);

        // Find cheapest options
        response.put("cheapestFlight", findCheapest(flights, "cost"));
        response.put("cheapestHotel", findCheapest(hotels, "pricePerNight"));
        response.put("cheapestTransportation", findCheapest(transportations, "cost"));

        // Calculate total cost with proper casting
        double totalCost = ((Double) ((Map<String, Object>) response.get("cheapestFlight")).get("cost"))
            + ((Double) ((Map<String, Object>) response.get("cheapestHotel")).get("pricePerNight"))
            + ((Double) ((Map<String, Object>) response.get("cheapestTransportation")).get("cost"));
        response.put("totalCost", totalCost);

        return response;
    }

    private Map<String, Object> findCheapest(List<Map<String, Object>> items, String costKey) {
        return items.stream()
            .min(Comparator.comparingDouble(item -> (Double) item.get(costKey)))
            .orElseThrow(() -> new RuntimeException("No items found"));
    }
}