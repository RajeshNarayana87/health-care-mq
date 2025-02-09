package com.healnet.healthcare.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healnet.healthcare.dto.Event;
import com.healnet.healthcare.exception.UnprocessableEntityException;
import com.healnet.healthcare.service.ClinicService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class DeleteEventListener {
    private final ClinicService clinicService;

    @JmsListener(destination = "${queue.name.delete}", containerFactory = "jmsFactory")
    public void receiveMessage(Message message) {
        try {
            String json = ((TextMessage) message).getText();
            Event event = new ObjectMapper().readValue(json, Event.class);
            log.info("Received Create message: : {}", event.toString());
            clinicService.deleteGroup(event);
        } catch (JsonProcessingException | JMSException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }
}
