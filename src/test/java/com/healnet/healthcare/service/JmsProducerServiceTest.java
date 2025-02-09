package com.healnet.healthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healnet.healthcare.constants.EventOperation;
import com.healnet.healthcare.dto.ClinicInfo;
import com.healnet.healthcare.dto.EventRequest;
import com.healnet.healthcare.dto.HospitalInfo;
import com.healnet.healthcare.service.implmentations.JmsProducerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.properties")
class JmsProducerServiceTest {
    @Value("${queue.name.create}")
    private String createQueue;

    @Value("${queue.name.delete}")
    private String deleteQueue;

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private ClinicService clinicService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HospitalService hospitalService;

    @InjectMocks
    private JmsProducerServiceImpl jmsProducer;

    @BeforeEach
    void setup() {
        this.jmsProducer = new JmsProducerServiceImpl(clinicService, hospitalService, jmsTemplate, objectMapper);
        jmsProducer.setCreateQueue(createQueue);
        jmsProducer.setDeleteQueue(deleteQueue);
    }

    @Test
    void testCreateQueueNameProperties() {
        assertNotNull(createQueue);
    }

    @Test
    void testDeleteQueueNameProperties() {
        assertNotNull(deleteQueue);
    }

    @Test
    void testCreateOperationSendMessage() {
        var eventRequest = new EventRequest(1L, 1L, "Cardiology", EventOperation.CREATE);

        when(hospitalService.getHospitalInfo(anyLong())).thenReturn(new HospitalInfo(1L, "KMC"));
        when(clinicService.getClinicInfo(anyLong())).thenReturn(new ClinicInfo(1L, 1L, "Cardiology"));

        jmsProducer.sendMessage(eventRequest);

        Mockito.verify(jmsTemplate, times(1)).send(eq("queue.create"), any());
    }

    @Test
    void testCreateOperationSendMessage_whenHospitalIdNotFound_thenExceptionThrown() {
        var eventRequest = new EventRequest(1L, 1L, "Cardiology", EventOperation.CREATE);

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            jmsProducer.sendMessage(eventRequest);
        });

        assertEquals("Provided Parent Group Id is invalid", thrownException.getMessage());

        Mockito.verify(jmsTemplate, times(0)).send(eq("queue.create"), any());
    }

    @Test
    void testDeleteOperationSendMessage() {
        var eventRequest = new EventRequest(1L, 1L, "Cardiology", EventOperation.DELETE);
        when(hospitalService.getHospitalInfo(anyLong())).thenReturn(new HospitalInfo(1L, "KMC"));
        when(clinicService.getClinicInfo(anyLong())).thenReturn(new ClinicInfo(1L, 1L, "Cardiology"));

        jmsProducer.sendMessage(eventRequest);

        Mockito.verify(jmsTemplate, times(1)).send(eq("queue.delete"), any());
    }

    @Test
    void testDeleteOperationSendMessage_whenClinicIdNotFound_thenExceptionThrown() {
        var eventRequest = new EventRequest(1L, 1L, "Cardiology", EventOperation.DELETE);

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            jmsProducer.sendMessage(eventRequest);
        });

        assertEquals("Provided Group Id is invalid", thrownException.getMessage());

        Mockito.verify(jmsTemplate, times(0)).send(eq("queue.delete"), any());
    }

    @Test
    void testDeleteOperationSendMessage_whenHospitalIdNotFound_thenExceptionThrown() {
        var eventRequest = new EventRequest(1L, 1L, "Cardiology", EventOperation.DELETE);

        when(clinicService.getClinicInfo(anyLong())).thenReturn(new ClinicInfo(1L, 1L, "Cardiology"));

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            jmsProducer.sendMessage(eventRequest);
        });

        assertEquals("Provided Parent Group Id is invalid", thrownException.getMessage());

        Mockito.verify(jmsTemplate, times(0)).send(eq("queue.delete"), any());
    }
}
