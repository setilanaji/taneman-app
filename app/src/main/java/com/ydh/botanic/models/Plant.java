package com.ydh.botanic.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@Entity(tableName = "plants")
public class Plant {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int plantId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "slug")
    @SerializedName("slug")
    private String slug;

    @ColumnInfo(name = "synonym")
    @SerializedName("synonym")
    private String synonym;

    @ColumnInfo(name = "local")
    @SerializedName("local")
    private String local;

    @ColumnInfo(name = "characteristic")
    @SerializedName("characteristic")
    private String characteristic;

    @ColumnInfo(name = "image")
    @SerializedName("image")
    private String imageUrl;

//    @ColumnInfo(name = "garden")
//    @SerializedName("garden")
//    private String garden;

//    @SerializedName("gardens")
//    private List<Garden> gardens;

    @SerializedName("categories")
    private List<Category> categories;

    @SerializedName("more_photos")
    private List<Image> images;

    @ColumnInfo(name = "date_added")
    @SerializedName("date_added")
    private String dateAdded;

    @ColumnInfo(name = "is_favourite")
    @SerializedName("is_favourite")
    private boolean isFavourite;

    public Plant(int plantId, String name, String synonym, String local, String characteristic,
                 String imageUrl, List<Category> categories,
                 String dateAdded, String slug, List<Image> images, boolean isFavourite) {
        this.plantId = plantId;
        this.name = name;
        this.synonym = synonym;
        this.local = local;
        this.characteristic = characteristic;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.dateAdded = dateAdded;
        this.slug = slug;
        this.images = images;
        this.isFavourite = isFavourite;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
