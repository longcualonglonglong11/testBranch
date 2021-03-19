package common;

import com.fts.common.api.common.payload.data.impl.AppIdDataRequest;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestRepRequestAppId extends AppIdDataRequest {
    private static final long serialVersionUID = 1L;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Integer> data;

    public TestRepRequestAppId(String message) {
        this.message = message;
    }

    public TestRepRequestAppId(String message, List<Integer> data) {
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

    @Override
    protected String generateDataInput() {
        return String.format("%s|%s", message, new Gson().toJson(data));
    }
}