//package com.example.recommendationservice.service.adapter;
//
//import com.example.recommendationservice.service.adapter.utility.JsonUtils;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class PexelsApiAdapter {
//
//    private final RestTemplate restTemplate;
//
//    @Value("${pexels.api.url}")
//    private String pexelsApiUrl;
//
//    @Value("${pexels.api.key}")
//    private String apiKey;
//
//    public PexelsApiAdapter(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public String fetchImageForPlace(String placeName) throws JsonProcessingException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", apiKey);
//
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        String url = String.format("%s/search?query=%s&per_page=1", pexelsApiUrl, placeName);
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode rootNode = objectMapper.readTree(response.getBody());
//        JsonNode photosNode = rootNode.path("photos");
//        if (photosNode.isEmpty()){
//           return  "No image found";
//        }
//        JsonNode firstPhoto = photosNode.get(0);
//        JsonNode srcNode = firstPhoto.path("src");
//        return srcNode.path("original").asText();
//    }
//}
