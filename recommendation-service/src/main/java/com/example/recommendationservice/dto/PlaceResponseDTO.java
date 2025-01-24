package com.example.recommendationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PlaceResponseDTO {
    private String place_name;
    private String description;
    private String website_link;
    private String image_link;

    public String getPlace_name() {
        return place_name;
    }



    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public PlaceResponseDTO(String place_name, String description, String website_link, String image_link) {
        this.place_name = place_name;
        this.description = description;
        this.website_link = website_link;
        this.image_link = image_link;
    }
}