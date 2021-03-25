package com.fts.training.dto;

import com.fts.icb.common.grpc.basegrpc.serializable.base.BaseClientResponse;
import com.google.gson.annotations.SerializedName;

public class TestRepResponseBase extends BaseClientResponse {
    private static final long serialVersionUID = 1L;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Object data;

    public TestRepResponseBase() {
    }

    public TestRepResponseBase(String message, Object data) {
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
