//package com.example.recommendationservice.service.adapter;
//
//import com.example.recommendationservice.dto.PlaceDTO;
//import com.example.recommendationservice.requests.GeminiRequest;
//import com.example.recommendationservice.service.adapter.utility.JsonUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class GeminiApiAdapter {
//
//    private final RestTemplate restTemplate;
//
//    @Value("${gemini.api.key}")
//    private String apiKey;
//
//    public GeminiApiAdapter(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public List<PlaceDTO> fetchRecommendedPlaces(String destination, String budget, String preferences) {
//        String prompt = String.format(
//            "Where to visit in %s, with %s budget and preferences to %s.  Provide JSON with place_name, description, and website_link. If you do not find any working website give link to wikipedia page. No comments allowed in the JSON",
//            destination, budget, preferences
//        );
//
//        // Endpoint with API key as query parameter
//        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;
//        GeminiRequest request = new GeminiRequest(
//            Collections.singletonList(
//                new GeminiRequest.Content(
//                    Collections.singletonList(new GeminiRequest.Part(prompt))
//                )
//            )
//        );
//        String response = restTemplate.postForObject(url,request,String.class);
//        response = response.substring(response.indexOf("text") + 8, response.indexOf("role")-3);
//        System.out.println("response " + response);
//        return JsonUtils.convertJsonToPlaceDTOList(response);
//    }
//}
//
