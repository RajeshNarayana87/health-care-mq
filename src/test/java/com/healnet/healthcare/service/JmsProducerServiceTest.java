package com.healnet.healthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healnet.healthcare.constants.EventOperation;
import com.healnet.healthcare.dto.ClinicInfo;
import com.healnet.healthcare.dto.Event;
import com.healnet.healthcare.dto.EventRequest;
import com.healnet.healthcare.service.implmentations.JmsProducerServiceImpl;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class JmsProducerServiceTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private ClinicService clinicService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private JmsProducerServiceImpl jmsProducer;

    @BeforeEach
    void setup() {
        this.jmsProducer = new JmsProducerServiceImpl(jmsTemplate, clinicService, objectMapper);
    }

    @Test
    void testCreateOperationSendMessage() {
        var eventRequest = new EventRequest(1L, EventOperation.CREATE);

        when(clinicService.getClinicInfo(anyLong())).thenReturn(new ClinicInfo(1L, 1L, "KMC"));

        jmsProducer.sendMessage(eventRequest);

        Mockito.verify(jmsTemplate, times(1)).send(eq("queue.create"), any());
    }

    @Test
    void testCreateOperationSendMessage_whenClinicIdNotFound_thenExceptionThrown() {
        var eventRequest = new EventRequest(1L, EventOperation.CREATE);

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            jmsProducer.sendMessage(eventRequest);
        });

        assertEquals("Provided Group Id is invalid", thrownException.getMessage());

        Mockito.verify(jmsTemplate, times(0)).send(eq("queue.create"), any());
    }

    @Test
    void testDeleteOperationSendMessage() {
        var eventRequest = new EventRequest(1L, EventOperation.DELETE);
        when(clinicService.getClinicInfo(anyLong())).thenReturn(new ClinicInfo(1L, 1L, "KMC"));

        jmsProducer.sendMessage(eventRequest);

        Mockito.verify(jmsTemplate, times(1)).send(eq("queue.delete"), any());
    }

    @Test
    void testDeleteOperationSendMessage_whenClinicIdNotFound_thenExceptionThrown() {
        var eventRequest = new EventRequest(1L, EventOperation.DELETE);

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            jmsProducer.sendMessage(eventRequest);
        });

        assertEquals("Provided Group Id is invalid", thrownException.getMessage());

        Mockito.verify(jmsTemplate, times(0)).send(eq("queue.delete"), any());
    }


}
