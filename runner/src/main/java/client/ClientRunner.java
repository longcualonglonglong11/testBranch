package com.fts.common.api.test.client;

import brave.Span;
import brave.Tracer;
import client.config.ClientConfigTracing;
import client.sdk.TestClient;
import client.sdk.impl.TestClientImpl;
import com.fts.common.api.common.payload.data.DataResponse;
import com.fts.common.tracing.ITracing;
import com.fts.common.tracing.instance.TracingFactory;
import com.fts.icb.common.ioc.SpringApplicationContext;
import com.google.gson.Gson;
import common.TestRepResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRunner.class);
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ClientConfigTracing.class);
        context.refresh();
        SpringApplicationContext.setSharedApplicationContext(context);

        TestClient testClient = new TestClientImpl("localhost", 8090, 821300826210307L, "key_821300826210307", false);
        ITracing tracing = TracingFactory.getTracingInstance();
        Span testSpan = tracing.getNewTrace("TestSend");
        try (Tracer.SpanInScope wsTest = tracing.getScope(testSpan)) {
            for(int i = 0; i < 1; i++) {
                LOGGER.info("Send test {} ...", i);
                DataResponse<TestRepResponse> response = testClient.callGrpcTest("Hello world " + i);
                LOGGER.info("Response : {}", gson.toJson(response));
            }
//            for(int i = 0; i < 2; i++) {
//                LOGGER.info("Send test {} ...", i);
//                DataResponse<TestRepResponse> response = testClient.callGrpcTestTimeout("Hello world " + i);
//                LOGGER.info("Response : {}", gson.toJson(response));
//            }
        } finally {
            testSpan.finish();
        }

//        String accessToken = "5DELhdwIEd9A__QNuEfS2mpatykNhxBmCpt-mJIf-O4=.eyJleHBpcmVkVGltZSI6MTU3ODM5NDEyODA4NywidG9rZW5UeXBlIjoiQUNDRVNTX1RPS0VOIiwicm9sZXMiOlt7Ik1hbmFnZVN0b3JlTWVtYmVyIjo4NTk0NTMwMjA3MDM2NzYsIk1hbmFnZUNvbGRXYWxsZXQiOjg1OTQ1MzAyMDcwMzY3NywiVXBkYXRlU3RvcmUiOjg1OTQ1MzAyMDcwMzY3NSwiU3RvcmVWaWV3Ijo4NTk0NTMwMjA3MDM2NzQsIlN0b3JlV2l0aGRyYXciOjg1OTQ1MzAyMDcwMzY3MiwiU3RvcmVEZXBvc2l0Ijo4NTk0NTMwMjA3MDM2NzN9LHt9XSwiZXh0cmFEYXRhIjp7InVzZXJJZCI6Ijg1OTQ1MzAyMDc4NzY3MyIsImFwcFR5cGUiOiJXRUIifX0=";
//        DataResponse<TestRepResponse> response = testClient.callGrpcTestToken(821300826210307L, 821300826210307L, accessToken,"Hello world");
//        LOGGER.info("Response : {}", gson.toJson(response));
//        DataResponse<TestRepResponse> response2 = testClient.callGrpcTestToken(821300826210307L, 821300826210307L, accessToken,"Hello world 2");
//        LOGGER.info("Response : {}", gson.toJson(response2));
    }
}