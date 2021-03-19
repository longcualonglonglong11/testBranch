package server.handler;

import client.sdk.TestClient;
import client.sdk.UserProfileService;
import client.sdk.impl.TestClientImpl;
import client.sdk.impl.UserProfileServiceImpl;
import com.fts.common.api.common.constant.AppType;
import com.fts.common.api.common.constant.Protocol;
import com.fts.common.api.common.exception.PayloadException;
import com.fts.common.api.common.payload.data.DataResponse;
import com.fts.common.api.server.service.annotation.AuthorizePolicy;
import com.fts.common.api.server.service.annotation.HandlerService;
import com.fts.common.api.server.service.annotation.HandlerServiceClass;
import com.fts.common.api.server.utils.Parser;
import com.fts.icb.common.commonlibs.rbac.common.exception.InvalidTokenInformationException;
import com.fts.icb.common.ioc.SpringApplicationContext;
import com.google.gson.Gson;
import common.*;

import config.KafkaProducerConfig;
//import config.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;

@HandlerServiceClass
public class ServiceHandler extends com.fts.common.api.server.service.ServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);
    public ServiceHandler(){
        template = SpringApplicationContext.getBean(KafkaTemplate.class);
//        listener = new Listener();

    }
    /**
     * Call localhost:8080/test > stop service
     *
     * @param request
     * @return
     * @throws InterruptedException
     */

    UserProfileService userProfileService = new UserProfileServiceImpl();

    @HandlerService(method = METHOD_TEST_GET_BY_ID, path = "/userid/*")
    public DataResponse<TestRepResponse> getById(TestRepRequest request) throws InterruptedException {
        String[] pathInfos = request.getPathInfo().split("/");
        Long userId = Long.valueOf(pathInfos[pathInfos.length - 1]);
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        return new DataResponse<>(new TestRepResponse("Find by ID " + userId, userProfileService.getUserProfileById(userId)));
    }
//    private Listener listener;
    private KafkaTemplate<Integer, String> template;

    @HandlerService(method = 99, path = "/kafka")
    public DataResponse<TestRepResponse> a(TestRepRequest request) throws InterruptedException {

        template.send("users","long");
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
//        TestRepResponse repResponse = new TestRepResponse("Find all", userProfileService.findAll());
//        return new DataResponse<>(repResponse);
        TestRepResponse repResponse = new TestRepResponse("Kafka test", "");
        return new DataResponse<>(repResponse);
    }
    @HandlerService(method = MethodConstant.METHOD_TEST_GET, path = "/")
    public DataResponse<TestRepResponse> findAll(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        TestRepResponse repResponse = new TestRepResponse("Find all", userProfileService.findAll());
        return new DataResponse<>(repResponse);
    }
    TestClient testClient = new TestClientImpl("localhost", 8090, 821300826210307L, "key_821300826210307", false);
    private static final Gson gson = new Gson();

    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_FIND_BY_ID)
    public DataResponse<TestRepResponse> getByIdByGrpcThroughHttp(TestRepRequest request) throws InterruptedException {
        String[] pathInfos = request.getPathInfo().split("/");
        Long userId = Long.valueOf(pathInfos[pathInfos.length - 1]);
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        TestRepResponse repResponse = new TestRepResponse("Use Grpc to find by id", userProfileService.findAll());
        return new DataResponse<>(repResponse);
    }
    @HandlerService(path = "/grpc/userid/*")
    public DataResponse<TestRepResponse> getByIdByGrpc(TestRepRequest request) throws InterruptedException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<TestRepResponse> response = testClient.callGrpcFindById(request.getMessage());
        TestRepResponse repResponse = new TestRepResponse("Find by id using grpc", gson.toJson(response));
        return new DataResponse<>(repResponse);
    }
    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_GET)
    public DataResponse<TestRepResponse> findAllByGrpc(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        TestRepResponse repResponse = new TestRepResponse("Find all", userProfileService.findAll());
        return new DataResponse<>(repResponse);

    }
    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_CREATE)
    public DataResponse<TestRepResponse> createByGrpc(TestRepRequest request) throws InterruptedException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        String[] message = request.getMessage().split(",");
        long id = Long.parseLong(message[0].split(":")[1]);
        String name = String.valueOf(message[1].split(":")[1]);
        String email = String.valueOf(message[2].split(":")[1]);
        String phone = String.valueOf(message[3].split(":")[1]);

        try {
            if (userProfileService.create(id, name, email, phone))
                return new DataResponse<>(new TestRepResponse("Create", "Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataResponse<>(new TestRepResponse("Create", "Failure"));
    }
    @HandlerService(method = MethodConstant.METHOD_TEST_GRPC_UPDATE)
    public DataResponse<TestRepResponse> updateByGrpc(TestRepRequest request) throws InterruptedException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        String[] message = request.getMessage().split(",");
        long id = Long.parseLong(message[0].split(":")[1]);
        String name = String.valueOf(message[1].split(":")[1]);
        String email = String.valueOf(message[2].split(":")[1]);
        String phone = String.valueOf(message[3].split(":")[1]);

        try {
            if (userProfileService.update(id, name, email, phone))
                return new DataResponse<>(new TestRepResponse("Update", "Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataResponse<>(new TestRepResponse("Update", "Failure"));
    }

    @HandlerService(path = "/grpc/")
    public DataResponse<TestRepResponse> findAllByGrpcThroughHttp(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<TestRepResponse> response = testClient.callGrpcFindAll(request.getMessage());
        TestRepResponse repResponse = new TestRepResponse("Find all by grpc", gson.toJson(response));
        return new DataResponse<>(repResponse);

    }
    @HandlerService(path = "/grpc/create", proto = Protocol.POST)
    public DataResponse<TestRepResponse> createByGrpcThroughHttp(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<TestRepResponse> response = testClient.callGrpcCreate(request.getMessage());
        TestRepResponse repResponse = new TestRepResponse("Create by grpc", gson.toJson(response));
        return new DataResponse<>(repResponse);

    }
    @HandlerService(path = "/grpc/update", proto = Protocol.PUT)
    public DataResponse<TestRepResponse> updateByGrpcThroughHttp(TestRepRequest request) throws InterruptedException {

        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        DataResponse<TestRepResponse> response = testClient.callGrpcUpdate(request.getMessage());
        TestRepResponse repResponse = new TestRepResponse("Update by grpc", gson.toJson(response));
        return new DataResponse<>(repResponse);

    }
    @HandlerService(method = MethodConstant.METHOD_TEST_POST, path = "/create", proto = Protocol.POST)
    public DataResponse<TestRepResponse> create(TestRepRequest request) throws InterruptedException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        String[] message = request.getMessage().split(",");
        long id = Long.parseLong(message[0].split(":")[1]);
        String name = String.valueOf(message[1].split(":")[1]);
        String email = String.valueOf(message[2].split(":")[1]);
        String phone = String.valueOf(message[3].split(":")[1]);

        try {
            if (userProfileService.create(id, name, email, phone))
                return new DataResponse<>(new TestRepResponse("Create", "Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataResponse<>(new TestRepResponse("Create", "Failure"));
    }

    private final int METHOD_TEST_PUT = 100;

    @HandlerService(method = METHOD_TEST_PUT, path = "/update", proto = Protocol.PUT)
    public DataResponse<TestRepResponse> update(TestRepRequest request) throws InterruptedException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        String[] message = request.getMessage().split(",");
        long id = Long.parseLong(message[0].split(":")[1]);
        String name = String.valueOf(message[1].split(":")[1]);
        String email = String.valueOf(message[2].split(":")[1]);
        String phone = String.valueOf(message[3].split(":")[1]);

        try {
            if (userProfileService.update(id, name, email, phone))
                return new DataResponse<>(new TestRepResponse("Update", "Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataResponse<>(new TestRepResponse("Update", "Failure"));
    }

//    @HandlerService(method = MethodConstant.METHOD_TEST_GET, path = "/test")
//    public DataResponse<TestRepResponse> callGet(TestRepRequest request) throws InterruptedException {
//        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
//        Thread.sleep(9);
//        TestRepResponse repResponse = new TestRepResponse("Test successfully", request.getMessage());
//        return new DataResponse<>(repResponse);
//    }


    @HandlerService(method = MethodConstant.METHOD_TEST_GET_BASE)
    public DataResponse<TestRepResponseBase> callGetBase(TestRepRequest request) throws Exception {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        TestRepResponseBase repResponse = new TestRepResponseBase("Test successfully", request.getMessage());
//        return new DataResponse<>(repResponse);
        throw new Exception();
    }

    @HandlerService(path = "/test/1")
    public DataResponse<TestRepResponse> callGet1(TestRepRequest request) throws InterruptedException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        TestRepResponse repResponse = new TestRepResponse("Test successfully", request.getMessage());
        return new DataResponse<>(repResponse);
    }

    @HandlerService(path = "/test/2")
    public DataResponse<TestRepResponse> callGet2(TestRepRequest request) throws InterruptedException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
//        while (true) {
////            Thread.sleep(1500);
////            System.out.println("Call get 2");
//            if(1 != 1 ) {
//                break;
//            }
//        }
        Thread.sleep(9);
        TestRepResponse repResponse = new TestRepResponse("Test successfully", request.getMessage());
        return new DataResponse<>(repResponse);
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_GET_APP_ID, path = "/test/appid")
    public DataResponse<TestRepResponse> callGetAppId(TestRepRequestAppId request) throws InterruptedException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        TestRepResponse repResponse = new TestRepResponse("Test successfully", request.getMessage());
        while (true) {
            if (1 != 1) {
                break;
            }
        }
//        Thread.sleep(9);
        return new DataResponse<>(repResponse);
    }

    private final int METHOD_TEST_GET_BY_ID = 1000;


    @HandlerService(method = MethodConstant.METHOD_TEST_POST_APP_ID, path = "/test/appid", proto = Protocol.POST)
    public DataResponse<TestRepResponse> callPostAppId(TestRepRequestAppId request) {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        TestRepResponse repResponse = new TestRepResponse("Test successfully", request.getMessage());
        return new DataResponse<>(repResponse);
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_GET_TOKEN, path = "/test/token")
    @AuthorizePolicy(action = "service_1:action_1", resource = "market")
    public DataResponse<TestRepResponse> callGetToken(TestRepRequestToken request) throws PayloadException, InvalidTokenInformationException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        LOGGER.info("Payload : {}", Parser.toJsonString(request.getPayload()));

        TestRepResponse repResponse = new TestRepResponse("Test successfully", request.getMessage());
        return new DataResponse<>(repResponse);
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_GET_APP_ID_OR_TOKEN, path = "/test/app_id_and_token")
    @AuthorizePolicy(action = "service_1:action_1", resource = "*")
    public DataResponse<TestRepResponse> callGetAppIdOrToken(TestRepRequestAppIdOrToken request) throws PayloadException, InvalidTokenInformationException {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        LOGGER.info("Payload : {}", Parser.toJsonString(request.getPayload()));

        TestRepResponse repResponse = new TestRepResponse("Test successfully", request.getMessage());
        return new DataResponse<>(repResponse);
    }

    @HandlerService(method = MethodConstant.METHOD_TEST_POST_TOKEN, path = "/test/token", proto = Protocol.POST)
    public DataResponse<TestRepResponse> callPostToken(TestRepRequestToken request) throws PayloadException, InvalidTokenInformationException {
        return callGetToken(request);
    }

//    @HandlerService(method = MethodConstant.METHOD_TEST_POST, path = "/test", proto = Protocol.POST)
//    public DataResponse<TestRepResponse> callPost(TestRepRequest request){
//        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
//        TestRepResponse repResponse = new TestRepResponse("Test successfully", request.getMessage());
//        return new DataResponse<>(repResponse);
//    }

//    @HandlerService(method = MethodConstant.METHOD_TEST_GET_MATCH, path = "/test/*")
//    public DataResponse<TestRepResponse> callTest2(TestRepRequest request){
//        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
//        TestRepResponse repResponse = new TestRepResponse("Test successfully", request);
//        return new DataResponse<>(repResponse);
//
//    }

    @HandlerService(path = "/test/app_type/default")
    @AuthorizePolicy()
    public DataResponse<TestRepResponse> testAllowedAppTypeDefault(TestRepRequest request) {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        TestRepResponse repResponse = new TestRepResponse("Test successfully", request.getMessage());
        return new DataResponse<>(repResponse);
    }

    @HandlerService(path = "/test/app_type/api")
    @AuthorizePolicy(appType = AppType.API)
    public DataResponse<TestRepResponse> testAllowedAppTypeApi(TestRepRequest request) {
        LOGGER.info("Receive : Message " + Parser.toJsonString(request));
        TestRepResponse repResponse = new TestRepResponse("Test successfully", request.getMessage());
        return new DataResponse<>(repResponse);
    }
}