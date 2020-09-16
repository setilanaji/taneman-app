package com.ydh.botanic.source.remote.responses;

import com.google.gson.annotations.SerializedName;

public class AccountResponse {

    @SerializedName("pk")
    private int pk;

    @SerializedName("email")
    private String email;

    @SerializedName("token")
    private String token;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
