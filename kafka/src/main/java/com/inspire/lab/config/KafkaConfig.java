package com.inspire.lab.config;

import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Import({KafkaProducerConfig.class, KafkaConsumerConfig.class})
public class KafkaConfig {
}
