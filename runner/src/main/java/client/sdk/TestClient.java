package client.sdk;

import com.fts.common.api.common.payload.data.DataResponse;
import common.TestRepResponse;
import common.TestRepResponseBase;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface TestClient {
    DataResponse<TestRepResponse> callGrpcTest(String message);
    DataResponse<TestRepResponse> callGrpcFindAll(String message);
    DataResponse<TestRepResponse> callGrpcCreate(String message);
    DataResponse<TestRepResponse> callGrpcUpdate(String message);
    DataResponse<TestRepResponse> callGrpcFindById(String message);

    DataResponse<TestRepResponse> callGrpcTestToken(Long appId, Long storeId, String token, String message);
    DataResponse<TestRepResponse> callGrpcTestTimeout(String message);

    TestRepResponseBase callGrpcTestBase(String message);

    DataResponse<TestRepResponse> callGrpcTestAppId(Long appId, String message) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;
    DataResponse<TestRepResponse> callGrpcTestAppIdAndToken(Long appId, Long storeId, String token, String message) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;
}
