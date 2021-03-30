package com.fts.training.server;

import com.fts.common.api.server.CommonServer;
import com.fts.common.configuration.sdk.config.InitConfiguration;
import com.fts.icb.common.ioc.SpringApplicationContext;
import com.fts.training.config.AppConfig;
import com.fts.training.server.handler.ExceptionsHandler;
import com.fts.training.server.handler.UserProfileHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

public class Runner {
    private static final String secretKey = "key_821300826210307";
    private static final String base64EncodedJWTPublicKey = "LS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS0KTUlJQ0lqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FnOEFNSUlDQ2dLQ0FnRUF4WTJ4N0prSG9zSXVvMWI0YUdrYgpYWlpyd1JLdzlhajdJUUxWeklEZnBWVTA4bHR1QUl6elUzSkJoa09RQVJvVHk1ZVc4SWZuWHB5MjZDM1kzS1h2Cm5GTExLS1BZKzlnd2RaTUNUaCs3M0NPY2FSaWlSbDVLNWJDTVlMWGZoSE1HaEVaT0tvRHZEbVh1Y3Q5MFNqcUsKYW5SODdMeWNFd1lYWFlLMDRMWVlvckJqNXN3bGthbjlrQUtGR085VlF6eCswQWU3NXVQVmhYdXNaUFJ0V2J2bQpuRy81eFNKZVVOcFZQOGNMakVESndWbDZITU9qbCtoR2RSekV3TEdqdGRtb0c3Smh0MnhzSkt2cGdRdzE3VkQxCkRkUGlTbTlUbEZkRkVWdUdFdnc0SEJEMmswVlovM3lxbm9NRC9XR3N2eStqWGc0a1VKN2hWcDYvTkF6SVRPVW4KVktjMC91TDBxbUVlbS9XdHd3WHFHN2picE44UDJES3ZhcEt3dnNHYXB3dmZEeU40d3lxcVBJditIRXpCVW4wMQpCODQ4ciswS1JlQzJvTU9vR1R4M1hrRnpPcjBaZlIwWVpCRDdlQjUzZlZCVUpqV0w2ZEdZbW10Uk5KK1MvVzMxCnJNbFpJSlpBWmd2NEd5YjFRREhMMkxRRVFaVWY3cHpacnRYOXJ6bkgyNDc3N0R3SmpzQ1VLZmFtZWEvMVV4bE0KaVFuYkltUkRZYittWkNNcVR4N3Z5c1dDWmNPS29TZHA5N3pYZUVFSzdnNzhTM056eVIxTU90TmxXOS9GR0psTwpTazBzSmU3WEZjOFlobEpuSTVocDJoRStCdTFxWC9GellnKzZib0VWdEFNZm1INGpNUWdEclU2cHRESTRVaitDCmkwK0J6TityQ0oweGx5NWNOcDdZVVZFQ0F3RUFBUT09Ci0tLS0tRU5EIFBVQkxJQyBLRVktLS0tLQ==";

    public static void main(String[] args) throws Exception {
        InitConfiguration initConfiguration = new InitConfiguration();

        initConfiguration.setConfigurationAnnotation(

                AppConfig.class
        );
        AnnotationConfigApplicationContext context = initConfiguration.getContext();
        context.refresh();
        SpringApplicationContext.setSharedApplicationContext(context);

        CommonServer commonServer = new CommonServer(
                secretKey,
                90D,
                base64EncodedJWTPublicKey
        );
        UserProfileHandler tmp = SpringApplicationContext.getBean(UserProfileHandler.class);
        commonServer.register(tmp);

        commonServer.register(new ExceptionsHandler());
        commonServer.initServlet(8080, 6, 1, TimeUnit.MINUTES);
        commonServer.initGrpc(8090, false, 6, 1, TimeUnit.MINUTES);
        commonServer.startServer();

    }
}
