package com.healnet.healthcare.config;

import jakarta.jms.JMSException;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JmsConsumerConfig {
    @JmsListener(destination = "test-queue")
    public void receiveMessage(String message) {
        try {
            log.info("Received message: : {}", message);
            if (message.contains("error")) {
                throw new JMSException("Simulated Processing Error");
            }
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
            throw new RuntimeException("Failed to process message", e);
        }
    }
}
