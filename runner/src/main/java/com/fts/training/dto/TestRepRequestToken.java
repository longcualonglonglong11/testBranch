package com.fts.training.dto;

import com.fts.common.api.common.payload.data.impl.TokenDataRequest;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestRepRequestToken extends TokenDataRequest {
    private static final long serialVersionUID = 1L;
    @SerializedName("app_id")
    private Long appId;
    @SerializedName("store_id")
    private Long storeId;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Integer> data;

    private long market;

    public TestRepRequestToken(Long appId, Long storeId, String token, String message) {
        super(token);
        this.appId = appId;
        this.storeId = storeId;
        this.message = message;
    }

    public TestRepRequestToken(Long appId, Long storeId, String token, String message, long market) {
        super(token);
        this.appId = appId;
        this.storeId = storeId;
        this.message = message;
        this.market = market;
    }

    public TestRepRequestToken(Long appId, Long storeId, String token, String message, List<Integer> data) {
        super(token);
        this.appId = appId;
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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public long getMarket() {
        return market;
    }

    public void setMarket(long market) {
        this.market = market;
    }
}