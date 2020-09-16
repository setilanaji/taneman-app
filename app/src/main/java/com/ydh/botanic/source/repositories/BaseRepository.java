package com.ydh.botanic.source.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ydh.botanic.models.ErrorMessage;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public abstract class BaseRepository {

    protected int SUCCESS_CODE = 200;
    protected String UNEXPECTED_ERROR_KEY = "Unexpected error";
    protected String UNEXPECTED_ERROR_MESSAGE = "Kesalahan tak terduga, coba lagi nanti!";
    protected int SESSION_EXPIRED_CODE = 401;

    protected ErrorMessage serializeErrorBody(ResponseBody response){
        Gson gson = new Gson();
        Type type = new TypeToken<ErrorMessage>() {
        }.getType();
        return gson.fromJson(response.charStream(), type);
    }
}
