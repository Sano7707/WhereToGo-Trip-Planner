package com.example.pexelservice.service;

import com.example.pexelservice.adapter.PexelsApiAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class PexelsService {

    private PexelsApiAdapter pexelsApiAdapter;

    public  PexelsService(PexelsApiAdapter pexelsApiAdapter){
        this.pexelsApiAdapter = pexelsApiAdapter;
    }

    public String fetchImageLinkFromPlaceName(String placeName) throws JsonProcessingException {
        return pexelsApiAdapter.fetchImageForPlace(placeName);
    }
}
