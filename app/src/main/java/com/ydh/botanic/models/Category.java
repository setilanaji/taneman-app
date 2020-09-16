package com.ydh.botanic.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Setter;

public class Category {

    @SerializedName("id")
    private int categoryId;

    @SerializedName("name")
    private String name;

    @SerializedName("slug")
    private String slug;

    @SerializedName("description")
    private String description;

    @SerializedName("plants")
    private List<Plant> plants;

    @SerializedName("image")
    private String image;

    @SerializedName("date_added")
    private String dateAdded;

    public Category(int categoryId, String name, String slug, String description, List<Plant> plants, String image, String dateAdded) {
        this.categoryId = categoryId;
        this.name = name;
        this.slug = slug;
        this.description = description;
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

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
}
