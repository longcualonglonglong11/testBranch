package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import server.handler.ServiceHandler;

import java.util.concurrent.CountDownLatch;

public class Listener {

    public final CountDownLatch latch1 = new CountDownLatch(1);
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);
    @KafkaListener(topics = "users")
    public void read(String data) {

        LOGGER.warn("KAFKA: " + data);
    }
}
