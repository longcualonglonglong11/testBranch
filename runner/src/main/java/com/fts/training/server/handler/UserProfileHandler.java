package com.fts.training.server.handler;

import com.fts.common.api.common.constant.Protocol;
import com.fts.common.api.common.payload.data.DataResponse;
import com.fts.common.api.server.service.ServiceHandler;
import com.fts.common.api.server.service.annotation.HandlerService;
import com.fts.common.api.server.service.annotation.HandlerServiceClass;
import com.fts.common.api.server.utils.Parser;
import com.fts.dbservice.sdk.exception.DBClusterException;
import com.fts.training.buz.UserProfileBuz;
import com.fts.training.client.sdk.Client;
import com.fts.training.dto.MethodConstant;
import com.fts.training.dto.RepResponse;
import com.fts.training.dto.TestRepRequest;
import com.fts.training.service.UserProfileService;
import com.google.gson.Gson;
import com.inspire.lab.request.UserRequest;
import com.inspire.lab.request.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@HandlerServiceClass
public class UserProfileHandler extends ServiceHandler implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);
    private static final Gson gson = new Gson();
    private final int METHOD_TEST_PUT = 100;
    @Autowired
//    @Qualifier("userProfileServiceImpl2")
    UserProfileService userProfileService;
    @Autowired
    Client client;
    @Autowired
    UserProfileBuz userProfileBuz;
    private ApplicationContext applicationContext;

    public UserProfileHandler() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @PostConstruct
    public void initMethod() {
        LOGGER.debug(Arrays.toString(applicationContext.getBeanDefinitionNames()));
    }

    /**
     * Call localhost:8080/test > stop com.fts.trainning.service
     *
     * @param request
     * @return
     * @throws InterruptedException
     */


    @HandlerService(method = MethodConstant.METHOD_TEST_GET_BY_ID, path = "/userid/*")
    public DataResponse<UserResponse> getById(TestRepRequest request) throws InterruptedException, DBClusterException {
        String[] pathInfos = request.getPathInfo().split("/");
        Long userId = Long.valueOf(pathInfos[pathInfos.length - 1]);
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        UserResponse response = userProfileBuz.getUserProfileById(userId);
        return new DataResponse<>(response);
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_GET, path = "/")
    public DataResponse<RepResponse> findAll(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        RepResponse repResponse = new RepResponse("Find all", userProfileService.findAll());
        return new DataResponse<>(repResponse);
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_FIND_BY_ID)
    public DataResponse<UserResponse> getByIdByGrpcThroughHttp(TestRepRequest request) throws InterruptedException, DBClusterException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        if (request.getMessage() == null || request.getMessage().isEmpty())
            throw new InterruptedException();
        UserResponse userResponse = userProfileBuz.getUserProfileById(Long.valueOf(request.getMessage()));
        return new DataResponse<>(userResponse);
    }

    @HandlerService(path = "/grpc/userid/*")
    public DataResponse<UserResponse> getByIdByGrpc(TestRepRequest request) throws InterruptedException {
        String[] pathInfos = request.getPathInfo().split("/");
        String userId = pathInfos[pathInfos.length - 1];
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<UserResponse> response = client.callGrpcFindById(userId);
        return response;
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_GET)
    public DataResponse<RepResponse> findAllByGrpc(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        RepResponse repResponse = new RepResponse("Find all", userProfileService.findAll());
        return new DataResponse<>(repResponse);

    }

    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_CREATE)
    public DataResponse<UserResponse> createByGrpc(UserRequest request) throws Exception {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        UserResponse response = userProfileBuz.handleRequest(request);
        return new DataResponse<>(response);
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_UPDATE)
    public DataResponse<UserResponse> updateByGrpc(UserRequest request) throws Exception {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        UserResponse response = userProfileBuz.update(request);
        return new DataResponse<>(response);

    }

    @HandlerService(path = "/grpc/")
    public DataResponse<RepResponse> findAllByGrpcThroughHttp(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<RepResponse> response = client.callGrpcFindAll(request.getMessage());
        return new DataResponse<>(response.getResponseCode(), "Find all by Grpc", response.getData());

    }

    @HandlerService(path = "/grpc/create", proto = Protocol.POST)
    public DataResponse<UserResponse> createByGrpcThroughHttp(UserRequest request) throws Exception {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<UserResponse> response = client.callGrpcCreate(request);
        return response;

    }

    @HandlerService(path = "/grpc/update", proto = Protocol.PUT)
    public DataResponse<UserResponse> updateByGrpcThroughHttp(UserRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<UserResponse> response = client.callGrpcUpdate(request);
        return response;
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_POST, path = "/create", proto = Protocol.POST)
    public DataResponse<UserResponse> create(UserRequest request) throws Exception {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        UserResponse response = userProfileBuz.handleRequest(request);
        return new DataResponse<>(response);

    }

    @HandlerService(method = METHOD_TEST_PUT, path = "/update", proto = Protocol.PUT)
    public DataResponse<UserResponse> update(UserRequest request) throws Exception {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        UserResponse response = userProfileBuz.update(request);
        return new DataResponse<>(response);

    }


}