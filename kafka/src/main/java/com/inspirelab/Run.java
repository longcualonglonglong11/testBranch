package com.inspirelab;

import com.inspirelab.broker.KafkaConstant;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.Acknowledgment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Run {
//    @Autowired
//    private Listener listener;
//
//    @Autowired
//    private KafkaTemplate<Integer, String> template;
//
//    @Configuration
//    @EnableKafka
//    public class Config {
//
//        @Bean
//        ConcurrentKafkaListenerContainerFactory<Integer, String>
//        kafkaListenerContainerFactory() {
//            ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
//                    new ConcurrentKafkaListenerContainerFactory<>();
//            factory.setConsumerFactory(consumerFactory());
//            return factory;
//        }
//
//        @Bean
//        public ConsumerFactory<Integer, String> consumerFactory() {
//            return new DefaultKafkaConsumerFactory<>(consumerProps());
//        }
//
//        private Map<String, Object> consumerProps() {
//            Map<String, Object> props = new HashMap<>();
//            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//            props.put(ConsumerConfig.GROUP_ID_CONFIG, "1");
//            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
//            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//            return props;
//        }
//
//        @Bean
//        public Listener listener() {
//            return new Listener();
//        }
//
//        @Bean
//        public ProducerFactory<Integer, String> producerFactory() {
//            return new DefaultKafkaProducerFactory<>(senderProps());
//        }
//
//        private Map<String, Object> senderProps() {
//            Map<String, Object> props = new HashMap<>();
//            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//            props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
//            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
//            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//            return props;
//        }
//
//        @Bean
//        public KafkaTemplate<Integer, String> kafkaTemplate() {
//            return new KafkaTemplate<Integer, String>(producerFactory());
//        }
//
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//
//
//    }
//    @Test
//    public void testSimple() throws Exception {
//        template.send("annotated1", 0, "foo");
//        template.flush();
////        assertTrue(this.listener.latch1.await(10, TimeUnit.SECONDS));
//    }
//     void a() throws InterruptedException {
//        template.send("annotated1", 0, "foo");
//        template.flush();
//        this.listener.latch1.await(10, TimeUnit.SECONDS);
//    }
//    public class Listener {
//
//        private final CountDownLatch latch1 = new CountDownLatch(1);
//
//        @KafkaListener(id = "foo", topics = "annotated1")
//        public void listen1(String foo) {
//            this.latch1.countDown();
//        }
//
//    }


    @Autowired
    private Listener listener;
    @Autowired
    private KafkaTemplate<Integer, String> template;

    public  void run() {
        template.send("users", 0, "foo");
        template.flush();
//        assertTrue(this.listener.latch1.await(10, TimeUnit.SECONDS));
    }



}
