package com.healnet.healthcare.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healnet.healthcare.dto.Event;
import com.healnet.healthcare.exception.UnprocessableEntityException;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DLQListener {

    @JmsListener(destination = "${queue.name.dl-queue}", containerFactory = "jmsFactory")
    public void receiveMessage(Message message) {
        try {
            String json = ((TextMessage) message).getText();
            Event event = new ObjectMapper().readValue(json, Event.class);
            log.info("Received DLQueue message: : {}", event.toString());
        } catch (JsonProcessingException | JMSException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }
}
