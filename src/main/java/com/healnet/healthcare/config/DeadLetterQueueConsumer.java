package com.healnet.healthcare.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DeadLetterQueueConsumer {

    @JmsListener(destination = "DLQ.test-queue")
    public void receiveDLQMessage(String message) {
        log.warn("Message Received from DLQ: {}", message);
        // Here, we could log, alert, or retry processing.
    }
}

