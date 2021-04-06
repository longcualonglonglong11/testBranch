package com.fts.training.client.sdk;

import com.fts.common.api.common.payload.data.DataResponse;
import com.fts.training.dto.RepResponse;
import com.fts.training.dto.TestRepResponseBase;
import com.inspire.lab.request.UserRequest;
import com.inspire.lab.request.UserResponse;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface Client {
    DataResponse<RepResponse> callGrpcTest(String message);

    DataResponse<RepResponse> callGrpcFindAll(String message);

    DataResponse<UserResponse> callGrpcCreate(UserRequest message);

    DataResponse<UserResponse> callGrpcUpdate(UserRequest message);

    DataResponse<UserResponse> callGrpcFindById(String message);

    DataResponse<RepResponse> callGrpcTestToken(Long appId, Long storeId, String token, String message);

    DataResponse<RepResponse> callGrpcTestTimeout(String message);

    TestRepResponseBase callGrpcTestBase(String message);

    DataResponse<RepResponse> callGrpcTestAppId(Long appId, String message) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;

    DataResponse<RepResponse> callGrpcTestAppIdAndToken(Long appId, Long storeId, String token, String message) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;
}
