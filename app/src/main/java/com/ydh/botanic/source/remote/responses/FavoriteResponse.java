package com.ydh.botanic.source.remote.responses;

import com.google.gson.annotations.SerializedName;

public class FavoriteResponse {

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
