package com.example.pexelservice.controller;

import com.example.pexelservice.service.PexelsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PexelsController {

    private PexelsService pexelsService;

    public PexelsController(PexelsService pexelsService){
        this.pexelsService = pexelsService;
    }

    @GetMapping("/api/image-link")
    public Map<String,String> getImageLink(@RequestParam String placeName) throws JsonProcessingException {
        Map<String,String> response = new HashMap<>();
        response.put("image_link",pexelsService.fetchImageLinkFromPlaceName(placeName));
        return response;
    }
}
