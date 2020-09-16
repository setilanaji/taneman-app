package com.ydh.botanic.models;

import com.google.gson.annotations.SerializedName;

public class ResponseModel <T> {
    private T response;

    private int code;

//    @SerializedName("data")
    private ErrorMessage errorMessage;

//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }

    public ResponseModel() {
        this.errorMessage = new ErrorMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResponse() {
        return response;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
