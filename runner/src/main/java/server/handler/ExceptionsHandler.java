package server.handler;

import com.fts.common.api.common.payload.message.BaseMessageResponse;
import com.fts.common.api.server.exception.ExceptionHandler;
import com.fts.common.api.server.exception.annotation.HandlerExceptionClass;

import java.util.concurrent.TimeoutException;

@HandlerExceptionClass
public class ExceptionsHandler extends com.fts.common.api.server.exception.ExceptionHandler {
    @Override
    public BaseMessageResponse timeoutException(TimeoutException e) {
        String message = "Request timeout";
        int code = 0;
        int status = 200;
        return new BaseMessageResponse(message,code, status);
    }

    @Override
    public BaseMessageResponse runTimeException(Exception e) {
        String message = e.getMessage() != null ? e.getMessage() : e.toString();
        int code = 0;
        int status = 200;
        return new BaseMessageResponse(message,code, status);
    }

//    @HandlerException
//    public BaseMessageResponse testException(TestException e) {
//        String message = e.getMessage() != null ? e.getMessage() : e.toString();
//        int code = -12;
//        int status = 500;
//        return new BaseMessageResponse(message,code, status);
//    }
}
