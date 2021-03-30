package com.fts.training.buz;


import com.inspire.lab.request.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;

public class MessageProducer {
    public static final String CREATE_USER_PROFILE = "users";
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);
    @Autowired
    KafkaTemplate<String, UserRequest> transactionItemRepKafkaTemplate;

    public Boolean sendCreateUserProfile(UserRequest msg) {
        return pushMsgKafka(CREATE_USER_PROFILE, msg);
    }

    private Boolean pushMsgKafka(String topic, UserRequest msg) {
        final Boolean[] status = {false};
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ListenableFuture<SendResult<String, UserRequest>> future = transactionItemRepKafkaTemplate.send(topic, msg);
        future.addCallback(new ListenableFutureCallback<SendResult<String, UserRequest>>() {
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error(String.format("Unable to send topic=[%s] key=[%s] due to : %s", ex.getMessage(), ex));
                countDownLatch.countDown();
            }

            @Override
            public void onSuccess(SendResult<String, UserRequest> result) {
                LOGGER.info("Sent topic=[{}] with offset=[{}]", result.getRecordMetadata().offset());
//                System.out.println("Sent message=[" + topic +
//                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
                status[0] = true;
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            LOGGER.error("[sendSubmittedTransaction] ", e);
        }
        return status[0];
    }
}
