package com.healnet.healthcare.service;

import com.healnet.healthcare.service.implmentations.JmsProducerServiceImpl;
import jakarta.jms.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class JmsProducerServiceTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private Queue queue;

    @InjectMocks
    private JmsProducerServiceImpl jmsProducer;

    @BeforeEach
    void setup() {
        this.jmsProducer = new JmsProducerServiceImpl(jmsTemplate, queue);
    }

    @Test
    void testSendMessage() {
        String message = "Hello JMS";

        jmsProducer.sendMessage(message);

        Mockito.verify(jmsTemplate, times(1)).convertAndSend(queue, message);
    }
}
