package com.example.geminiservice.controller;

import com.example.geminiservice.dto.PlaceDTO;
import com.example.geminiservice.service.GeminiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeminiController {

    private GeminiService geminiService;

    public GeminiController(GeminiService geminiService){
        this.geminiService = geminiService;
    }

    @GetMapping("/api/gemini-response")
    public List<PlaceDTO> getResponseFromGemini(String destination, String budget, String preferences){
        return geminiService.getResponseFromGemini(destination,budget,preferences);
    }
}
