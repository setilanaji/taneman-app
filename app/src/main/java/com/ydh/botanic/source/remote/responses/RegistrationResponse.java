package com.ydh.botanic.source.remote.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("response")
    @Expose
    private String response;

//    @SerializedName("error_message")
//    @Expose
//    private String errorMessage;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("is_active")
    @Expose
    private boolean isActive;

//    @SerializedName("token")
//    @Expose
//    private String token;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

//    public String getErrorMessage() {
//        return errorMessage;
//    }
//
//    public void setErrorMessage(String errorMessage) {
//        this.errorMessage = errorMessage;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
