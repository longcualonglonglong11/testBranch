package com.inspire.lab.request;

import com.fts.common.api.common.payload.data.impl.BaseDataRequest;
import com.google.gson.annotations.SerializedName;

public class UserRequest extends BaseDataRequest {
    private static final long serialVersionUID = 1L;

    @SerializedName("user_id")
    private Long userID;
    @SerializedName("user_name")
    private String name;
    @SerializedName("user_phone")
    private String phone;
    @SerializedName("user_email")
    private String email;
    private Enum state;

    public UserRequest() {
    }


    public UserRequest(Long userID, String name, String phone, String email) {
        this.userID = userID;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Enum getState() {
        return state;
    }

    public void setState(Enum state) {
        this.state = state;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
