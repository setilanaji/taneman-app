package com.ydh.botanic.models;

import com.google.gson.annotations.SerializedName;

public class ErrorMessage {

//    @SerializedName("response")
    private String response;
//    @SerializedName("key")
    private String key;
//    @SerializedName("error_message")
    private String error_message;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
//    public ErrorMessage(String response, String key, String error_message) {
//        this.response = response;
//        this.key = key;
//        this.error_message = error_message;
//    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
