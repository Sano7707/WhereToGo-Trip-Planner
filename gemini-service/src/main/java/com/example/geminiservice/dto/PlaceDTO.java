package com.example.geminiservice.dto;


public class PlaceDTO {
    private String place_name;
    private String description;
    private String website_link;
    private String image_link = "";

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

    public String getImageLink() {
        return image_link;
    }

    public void setImageLink(String imageLink) {
        this.image_link = imageLink;
    }


}