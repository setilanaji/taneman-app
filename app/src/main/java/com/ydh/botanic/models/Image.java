package com.ydh.botanic.models;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id")
    private int id;

    @SerializedName("imagefile")
    private String imageUrl;

    public Image(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
