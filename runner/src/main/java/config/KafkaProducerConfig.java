package config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import server.handler.ServiceHandler;

import java.util.HashMap;
import java.util.Map;


//@PropertySource("classpath:application.properties")
//public class KafkaProducerConfig {
//    @Value(value = "${kafka.bootstrapAddress}")
//    private String bootstrapAddress;
//
//}
@Configuration
public class KafkaProducerConfig {
//    @ConfigService("payment_gateway_kafka")
//    public static ResourceConfig kafka;

    @Bean
    public ProducerFactory<String, ?> transactionRepFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, ?> transactionRepKafkaTemplate() {
        return new KafkaTemplate<>(transactionRepFactory());
    }

}