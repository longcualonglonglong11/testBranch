package com.fts.training.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RepResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Object data;

    public RepResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
