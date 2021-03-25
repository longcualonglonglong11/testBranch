package com.fts.training.dto;

import com.inspire.lab.config.Listener;
import org.springframework.stereotype.Component;

@Component
public class CustomConsumer extends Listener {
    @Override
    public void response(String data) {
        super.response(data);
    }
}
