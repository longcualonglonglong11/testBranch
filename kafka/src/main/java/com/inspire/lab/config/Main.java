package com.inspire.lab.config;

import com.fts.common.configuration.sdk.config.InitConfiguration;
import com.fts.common.configuration.sdk.exception.GetConfigException;
import com.fts.common.configuration.sdk.exception.InvalidFieldException;
import com.fts.common.configuration.sdk.exception.NotFoundException;
import com.fts.icb.common.ioc.SpringApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InvalidFieldException, IOException, GetConfigException, NotFoundException, IllegalAccessException {
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//        ctx.register(KafkaProducerConfig.class, KafkaConsumerConfig.class);
//        ctx.refresh();
//        KafkaTemplate<String, String> template = ctx.getBean(KafkaTemplate.class);
        InitConfiguration initConfiguration = new InitConfiguration();

        initConfiguration.setConfigurationAnnotation(
              KafkaConfig.class
        );
        AnnotationConfigApplicationContext context = initConfiguration.getContext();
        context.refresh();
        SpringApplicationContext.setSharedApplicationContext(context);


    }


}
