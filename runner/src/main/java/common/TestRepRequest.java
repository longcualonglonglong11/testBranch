package common;

import com.fts.common.api.common.payload.data.impl.TokenDataRequest;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestRepRequest extends TokenDataRequest {
    private static final long serialVersionUID = 1L;
    @SerializedName("message")
    private String message;
    @SerializedName("list")
    private List<Integer> list;
    @SerializedName("data")
    private Integer data;

    public TestRepRequest(String message) {
        super();
        this.message = message;
    }

    public TestRepRequest(String message, List<Integer> list, Integer data) {
        this.message = message;
        this.list = list;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}