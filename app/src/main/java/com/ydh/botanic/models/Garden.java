package com.ydh.botanic.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Garden {

    @SerializedName("id")
    private int garden_id;

    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String description;

    @SerializedName("slug")
    private String slug;

    @SerializedName("location_long")
    private float locLong;

    @SerializedName("location_lat")
    private float locLat;

    @SerializedName("plants")
    private List<Plant> plants;

    @SerializedName("image")
    private String image;

    @SerializedName("date_added")
    private String dateAdded;


    public Garden(int garden_id, String name, String description, String slug, float locLong, float locLat, List<Plant> plants, String image, String dateAdded) {
        this.garden_id = garden_id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.locLong = locLong;
        this.locLat = locLat;
        this.plants = plants;
        this.image = image;
        this.dateAdded = dateAdded;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getGarden_id() {
        return garden_id;
    }

    public void setGarden_id(int garden_id) {
        this.garden_id = garden_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLocLong() {
        return locLong;
    }

    public void setLocLong(float locLong) {
        this.locLong = locLong;
    }

    public float getLocLat() {
        return locLat;
    }

    public void setLocLat(float locLat) {
        this.locLat = locLat;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
