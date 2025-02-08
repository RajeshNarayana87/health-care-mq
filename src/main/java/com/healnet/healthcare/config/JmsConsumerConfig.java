package com.healnet.healthcare.config;

import com.healnet.healthcare.dto.Event;
import com.healnet.healthcare.exception.UnprocessableEntityException;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JmsConsumerConfig {
    @JmsListener(destination = "EVENT.CREATE", id = "listener-1")
    public void receiveCreateMessage(Event message) {
        try {
            log.info("Received Create message: : {}", message.toString());
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
            throw new UnprocessableEntityException("Failed to process message");
        }
    }

    @JmsListener(destination = "EVENT.DELETE", id = "listener-2")
    public void receiveDeleteMessage(Event message) {
        try {
            log.info("Received Delete message: : {}", message.toString());
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
            throw new UnprocessableEntityException("Failed to process message");
        }
    }
}
