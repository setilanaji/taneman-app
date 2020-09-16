package com.ydh.botanic.models;

import com.google.gson.annotations.SerializedName;

public class Botanic {

    @SerializedName("id")
    private int botanicId;

    @SerializedName("name")
    private int name;

    @SerializedName("description")
    private int description;

    @SerializedName("address")
    private int address;

    @SerializedName("image")
    private int image;

    @SerializedName("website")
    private int website;

    public Botanic(int botanicId, int name, int description, int address, int image, int website) {
        this.botanicId = botanicId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.image = image;
        this.website = website;
    }

    public int getBotanicId() {
        return botanicId;
    }

    public void setBotanicId(int botanicId) {
        this.botanicId = botanicId;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getWebsite() {
        return website;
    }

    public void setWebsite(int website) {
        this.website = website;
    }
}
