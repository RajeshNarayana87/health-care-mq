package com.healnet.healthcare.service;

import com.healnet.healthcare.constants.EventOperation;
import com.healnet.healthcare.dto.ClinicInfo;
import com.healnet.healthcare.dto.Event;
import com.healnet.healthcare.dto.EventRequest;
import com.healnet.healthcare.service.implmentations.JmsProducerServiceImpl;
import jakarta.jms.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.healnet.healthcare.util.DateUtil.getCurrentTimeInEpoch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class JmsProducerServiceTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private ClinicService clinicService;

    @InjectMocks
    private JmsProducerServiceImpl jmsProducer;

    @BeforeEach
    void setup() {
        this.jmsProducer = new JmsProducerServiceImpl(jmsTemplate, clinicService);
    }

    @Test
    void testCreateOperationSendMessage() {
        var eventRequest = new EventRequest(1L, EventOperation.CREATE);
        var expectedEvent = new Event(1L, 1L, EventOperation.CREATE, getCurrentTimeInEpoch());
        when(clinicService.getClinicInfo(anyLong())).thenReturn(new ClinicInfo(1L,1L, "KMC"));
        ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        ArgumentCaptor<String> eventQueueCaptor = ArgumentCaptor.forClass(String.class);

        jmsProducer.sendMessage(eventRequest);

        Mockito.verify(jmsTemplate, times(1)).convertAndSend(eventQueueCaptor.capture(), eventArgumentCaptor.capture());
        var capturedEvent = eventArgumentCaptor.getValue();
        assertEquals(expectedEvent.getGroupId(), capturedEvent.getGroupId());
        assertEquals(expectedEvent.getParentGroupId(), capturedEvent.getParentGroupId());
        assertEquals(expectedEvent.getOperation(), capturedEvent.getOperation());
    }

    @Test
    void testCreateOperationSendMessage_whenClinicIdNotFound_thenExceptionThrown() {
        var eventRequest = new EventRequest(1L, EventOperation.CREATE);

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            jmsProducer.sendMessage(eventRequest);
        });

        assertEquals("Provided Group Id is invalid", thrownException.getMessage());

        Mockito.verify(jmsTemplate, times(0)).convertAndSend(any(Queue.class), any(Event.class));
    }

    @Test
    void testDeleteOperationSendMessage() {
        var eventRequest = new EventRequest(1L, EventOperation.DELETE);
        var expectedEvent = new Event(1L, 1L, EventOperation.DELETE, getCurrentTimeInEpoch());
        when(clinicService.getClinicInfo(anyLong())).thenReturn(new ClinicInfo(1L,1L, "KMC"));
        ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        ArgumentCaptor<String> eventQueueCaptor = ArgumentCaptor.forClass(String.class);

        jmsProducer.sendMessage(eventRequest);

        Mockito.verify(jmsTemplate, times(1)).convertAndSend(eventQueueCaptor.capture(), eventArgumentCaptor.capture());
        var capturedEvent = eventArgumentCaptor.getValue();
        assertEquals(expectedEvent.getGroupId(), capturedEvent.getGroupId());
        assertEquals(expectedEvent.getParentGroupId(), capturedEvent.getParentGroupId());
        assertEquals(expectedEvent.getOperation(), capturedEvent.getOperation());
    }

    @Test
    void testDeleteOperationSendMessage_whenClinicIdNotFound_thenExceptionThrown() {
        var eventRequest = new EventRequest(1L, EventOperation.DELETE);

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            jmsProducer.sendMessage(eventRequest);
        });

        assertEquals("Provided Group Id is invalid", thrownException.getMessage());

        Mockito.verify(jmsTemplate, times(0)).convertAndSend(any(Queue.class), any(Event.class));
    }
}
