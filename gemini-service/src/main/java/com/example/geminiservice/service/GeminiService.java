package com.example.geminiservice.service;

import com.example.geminiservice.dto.PlaceDTO;
import com.example.geminiservice.service.adapter.GeminiApiAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeminiService {

    private GeminiApiAdapter geminiApiAdapter;

    public GeminiService(GeminiApiAdapter geminiApiAdapter){
        this.geminiApiAdapter = geminiApiAdapter;
    }

    public List<PlaceDTO> getResponseFromGemini(String destination, String budget, String preferences){
        return geminiApiAdapter.fetchRecommendedPlaces(destination,budget,preferences);
    }

}
