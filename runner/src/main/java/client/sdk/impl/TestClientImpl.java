package client.sdk.impl;

import client.sdk.TestClient;
import com.fts.common.api.client.service.AbstractService;
import com.fts.common.api.common.payload.data.DataResponse;
import common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;

public class TestClientImpl extends AbstractService implements TestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestClientImpl.class);

    public TestClientImpl(String host, int port, Long appId, String appSecret, Boolean enableSSL) {
        super(host, port, 10*60*1000, appSecret, enableSSL);
    }

    public DataResponse<TestRepResponse> callGrpcTest(String message) {
        long start = System.currentTimeMillis();
        try {
            TestRepRequest repRequest = new TestRepRequest(message);
            return client.sendRequest(UUID.randomUUID().toString(), MethodConstant.METHOD_TEST_GET_BASE, repRequest);
        } finally {
            System.out.println("Request time : " + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public DataResponse<TestRepResponse> callGrpcFindAll(String message) {
        long start = System.currentTimeMillis();
        try {
            TestRepRequest repRequest = new TestRepRequest(message);
            return client.sendRequest(UUID.randomUUID().toString(), MethodConstant.METHOD_TEST_GRPC_GET, repRequest);
        } finally {
            System.out.println("Request time : " + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public DataResponse<TestRepResponse> callGrpcCreate(String message) {
        long start = System.currentTimeMillis();
        try {
            TestRepRequest repRequest = new TestRepRequest(message);
            return client.sendRequest(UUID.randomUUID().toString(), MethodConstant.METHOD_TEST_GRPC_CREATE, repRequest);
        } finally {
            System.out.println("Request time : " + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public DataResponse<TestRepResponse> callGrpcUpdate(String message) {
        long start = System.currentTimeMillis();
        try {
            TestRepRequest repRequest = new TestRepRequest(message);
            return client.sendRequest(UUID.randomUUID().toString(), MethodConstant.METHOD_TEST_GRPC_UPDATE, repRequest);
        } finally {
            System.out.println("Request time : " + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public DataResponse<TestRepResponse> callGrpcFindById(String message) {
        long start = System.currentTimeMillis();
        try {
            TestRepRequest repRequest = new TestRepRequest(message);
            return client.sendRequest(UUID.randomUUID().toString(), MethodConstant.METHOD_TEST_GRPC_FIND_BY_ID, repRequest);
        } finally {
            System.out.println("Request time : " + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public TestRepResponseBase callGrpcTestBase(String message) {
        long start = System.currentTimeMillis();
        try {
            TestRepRequest repRequest = new TestRepRequest(message);
            return client.sendRequestBaseGrpc(UUID.randomUUID().toString(), MethodConstant.METHOD_TEST_GET_BASE, repRequest, TestRepResponseBase.class);
        } finally {
            System.out.println("Request time : " + (System.currentTimeMillis() - start));
        }
    }

    public DataResponse<TestRepResponse> callGrpcTestAppId(Long appId, String message) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        long start = System.currentTimeMillis();
        try {
            TestRepRequestAppId repRequest = new TestRepRequestAppId(message);
            return client.sendRequest(UUID.randomUUID().toString(), MethodConstant.METHOD_TEST_GET_APP_ID, repRequest);
        } finally {
            System.out.println("Request time : " + (System.currentTimeMillis() - start));
        }
    }

    public DataResponse<TestRepResponse> callGrpcTestToken(Long appId, Long storeId, String token, String message) {
        long start = System.currentTimeMillis();
        try {
            TestRepRequestToken repRequest = new TestRepRequestToken(appId, storeId, token, message, 9999L);
            return client.sendRequest(UUID.randomUUID().toString(), MethodConstant.METHOD_TEST_GET_TOKEN, repRequest);
        } finally {
            System.out.println("Request time : " + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public DataResponse<TestRepResponse> callGrpcTestTimeout(String message) {
        long start = System.currentTimeMillis();
        try {
            TestRepRequest repRequest = new TestRepRequest(message);
            return client.sendRequest(UUID.randomUUID().toString(), MethodConstant.METHOD_TEST_GET_TIMEOUT, repRequest, new HashMap<String, String>(){{
                put("message", message);
            }}, "callGrpcTestTimeout");
        } finally {
            LOGGER.info("Request time : " + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public DataResponse<TestRepResponse> callGrpcTestAppIdAndToken(Long appId, Long storeId, String token, String message) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        long start = System.currentTimeMillis();
        try {
            TestRepRequestAppIdOrToken repRequest = new TestRepRequestAppIdOrToken(appId, storeId, token, message);
            return client.sendRequest(UUID.randomUUID().toString(), MethodConstant.METHOD_TEST_GET_APP_ID_OR_TOKEN, repRequest);
        } finally {
            System.out.println("Request time : " + (System.currentTimeMillis() - start));
        }
    }
}
