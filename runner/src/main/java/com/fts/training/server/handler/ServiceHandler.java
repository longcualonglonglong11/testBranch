package com.fts.training.server.handler;

import com.fts.training.client.sdk.Client;
import com.fts.training.service.UserProfileService;
import com.fts.training.client.sdk.impl.ClientImpl;
import com.fts.common.api.common.constant.Protocol;
import com.fts.common.api.common.payload.data.DataResponse;
import com.fts.common.api.server.service.annotation.HandlerService;
import com.fts.common.api.server.service.annotation.HandlerServiceClass;
import com.fts.common.api.server.utils.Parser;
import com.fts.icb.common.ioc.SpringApplicationContext;
import com.google.gson.Gson;
import com.fts.training.dto.MethodConstant;
import com.fts.training.dto.TestRepRequest;
import com.fts.training.dto.RepResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

@HandlerServiceClass
public class ServiceHandler extends com.fts.common.api.server.service.ServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);
    @Autowired
//    @Qualifier("userProfileServiceImpl2")
    UserProfileService userProfileService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ServiceHandler() {
        ApplicationContext context = SpringApplicationContext.getApplicationContext();

        String[] a = context.getBeanDefinitionNames();
//        kafkaTemplate = SpringApplicationContext.getBean(KafkaTemplate.class);
//        userProfileService = SpringApplicationContext.getBean(UserProfileService.class);

    }

    /**
     * Call localhost:8080/test > stop com.fts.trainning.service
     *
     * @param request
     * @return
     * @throws InterruptedException
     */


    @HandlerService(method = MethodConstant.METHOD_TEST_GET_BY_ID, path = "/userid/*")
    public DataResponse<RepResponse> getById(TestRepRequest request) throws InterruptedException {
        String[] pathInfos = request.getPathInfo().split("/");
        Long userId = Long.valueOf(pathInfos[pathInfos.length - 1]);
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        return new DataResponse<>(new RepResponse("Find by ID " + userId, userProfileService.getUserProfileById(userId)));
    }

    // Send message to kafka
    @HandlerService(method = MethodConstant.METHOD_TEST_SEND_MESSAGE_TO_KAFKA, path = "/kafka")
    public DataResponse<RepResponse> sendMessageToKafkaServer(TestRepRequest request) throws InterruptedException {
        kafkaTemplate.send("users", "hello inspirelab");
        RepResponse repResponse = new RepResponse("Kafka test", "Seding successfuly");
        return new DataResponse<>(null);
    }


    @HandlerService(method = MethodConstant.METHOD_TEST_GET, path = "/")
    public DataResponse<RepResponse> findAll(TestRepRequest request) throws InterruptedException {
        ApplicationContext context = SpringApplicationContext.getApplicationContext();

        String[] a = context.getBeanDefinitionNames();
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        RepResponse repResponse = new RepResponse("Find all", userProfileService.findAll());
        return new DataResponse<>(repResponse);
    }

    Client client = new ClientImpl("localhost", 8090, 821300826210307L, "key_821300826210307", false);
    private static final Gson gson = new Gson();

    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_FIND_BY_ID)
    public DataResponse<RepResponse> getByIdByGrpcThroughHttp(TestRepRequest request) throws InterruptedException {
        String[] pathInfos = request.getPathInfo().split("/");
        Long userId = Long.valueOf(pathInfos[pathInfos.length - 1]);
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        RepResponse repResponse = new RepResponse("Use Grpc to find by id", userProfileService.findAll());
        return new DataResponse<>(repResponse);
    }

    @HandlerService(path = "/grpc/userid/*")
    public DataResponse<RepResponse> getByIdByGrpc(TestRepRequest request) throws InterruptedException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<RepResponse> response = client.callGrpcFindById(request.getMessage());
        return response;
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_GET)
    public DataResponse<RepResponse> findAllByGrpc(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        RepResponse repResponse = new RepResponse("Find all", userProfileService.findAll());
        return new DataResponse<>(repResponse);

    }


    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_CREATE)
    public DataResponse<RepResponse> createByGrpc(TestRepRequest request) throws Exception {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        String[] message = request.getMessage().split(",");
        long id = Long.parseLong(message[0].split(":")[1]);
        String name = String.valueOf(message[1].split(":")[1]);
        String email = String.valueOf(message[2].split(":")[1]);
        String phone = String.valueOf(message[3].split(":")[1]);
        userProfileService.create(id, name, email, phone);
        return new DataResponse<>(new RepResponse("Create", "Successfully"));
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_UPDATE)
    public DataResponse<RepResponse> updateByGrpc(TestRepRequest request) throws Exception {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        String[] message = request.getMessage().split(",");
        long id = Long.parseLong(message[0].split(":")[1]);
        String name = String.valueOf(message[1].split(":")[1]);
        String email = String.valueOf(message[2].split(":")[1]);
        String phone = String.valueOf(message[3].split(":")[1]);

        userProfileService.update(id, name, email, phone);
        return new DataResponse<>(new RepResponse("Update", "Successfully"));

    }

    @HandlerService(path = "/grpc/")
    public DataResponse<RepResponse> findAllByGrpcThroughHttp(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<RepResponse> response = client.callGrpcFindAll(request.getMessage());
        return new DataResponse<>(response.getResponseCode(), "Find all by Grpc", response.getData());

    }

    @HandlerService(path = "/grpc/create", proto = Protocol.POST)
    public DataResponse<RepResponse> createByGrpcThroughHttp(TestRepRequest request) throws Exception {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<RepResponse> response = client.callGrpcCreate(request.getMessage());
        return response;

    }

    @HandlerService(path = "/grpc/update", proto = Protocol.PUT)
    public DataResponse<RepResponse> updateByGrpcThroughHttp(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<RepResponse> response = client.callGrpcUpdate(request.getMessage());
        return response;
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_POST, path = "/create", proto = Protocol.POST)
    public DataResponse<RepResponse> create(TestRepRequest request) throws Exception {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        String[] message = request.getMessage().split(",");
        long id = Long.parseLong(message[0].split(":")[1]);
        String name = String.valueOf(message[1].split(":")[1]);
        String email = String.valueOf(message[2].split(":")[1]);
        String phone = String.valueOf(message[3].split(":")[1]);

        userProfileService.create(id, name, email, phone);
        return new DataResponse<>(new RepResponse("Create", "Successfully"));

    }

    private final int METHOD_TEST_PUT = 100;

    @HandlerService(method = METHOD_TEST_PUT, path = "/update", proto = Protocol.PUT)
    public DataResponse<RepResponse> update(TestRepRequest request) throws Exception {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        String[] message = request.getMessage().split(",");
        long id = Long.parseLong(message[0].split(":")[1]);
        String name = String.valueOf(message[1].split(":")[1]);
        String email = String.valueOf(message[2].split(":")[1]);
        String phone = String.valueOf(message[3].split(":")[1]);

        userProfileService.update(id, name, email, phone);
        return new DataResponse<>(new RepResponse("Update", "Successfully"));

    }

}