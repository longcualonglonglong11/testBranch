package com.fts.training.dto;

import com.fts.common.api.common.payload.data.impl.BothVerifyDataRequest;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestRepRequestAppIdOrToken extends BothVerifyDataRequest {
    private static final long serialVersionUID = 1L;

    @SerializedName("store_id")
    private Long storeId;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Integer> data;

    public TestRepRequestAppIdOrToken(Long appId, Long storeId, String token, String message) {
        super(appId, token);
        this.storeId = storeId;
        this.message = message;
    }

    public TestRepRequestAppIdOrToken(Long appId, Long storeId, String token, String message, List<Integer> data) {
        super(appId, token);
        this.storeId = storeId;
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    protected String generateDataInput() {
        return message;
    }

    @Override
    public String getDataInput() {
        return message;
    }
}
