package com.inspire.lab.config;

import com.fts.common.api.server.service.ServiceHandler;
import com.inspire.lab.request.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);
    private static final String TOPIC_NAME = "users";
    private static final String GROUP_ID = "group_1";
    private static final String MESSAGE = "KAFKA response: ";

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void response(UserRequest data) throws Exception {
        LOGGER.warn(MESSAGE + data);
    }
}
