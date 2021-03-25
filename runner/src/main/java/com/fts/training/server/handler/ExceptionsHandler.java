package com.fts.training.server.handler;

import com.fts.common.api.common.payload.message.BaseMessageResponse;
import com.fts.common.api.server.exception.annotation.HandlerException;
import com.fts.common.api.server.exception.annotation.HandlerExceptionClass;

import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@HandlerExceptionClass
public class ExceptionsHandler extends com.fts.common.api.server.exception.ExceptionHandler {
    @Override
    public BaseMessageResponse timeoutException(TimeoutException e) {
        String message = "Request timeout";
        int code = 408;
        int status = 408;
        return new BaseMessageResponse(message, code, status);
    }

    @Override
    public BaseMessageResponse runTimeException(Exception e) {
        String message = e.getMessage() != null ? e.getMessage() : e.toString();
        int code = 500;
        int status = 500;
        return new BaseMessageResponse(message, code, status);
    }

    @HandlerException
    public BaseMessageResponse sqlException(SQLException e) {
        String message = e.getMessage() != null ? e.getMessage() : "SQL Exception";
        int code = 502;
        int status = 502;
        return new BaseMessageResponse(message, code, status);
    }
}
