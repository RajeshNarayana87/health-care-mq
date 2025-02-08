package com.healnet.healthcare.config;

import com.healnet.healthcare.constants.EventOperation;
import com.healnet.healthcare.dto.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static com.healnet.healthcare.util.DateUtil.getCurrentTimeInEpoch;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
class JmsConsumerConfigTest {

    @InjectMocks
    private JmsConsumerConfig jmsConsumer;

    @BeforeEach
    void setup() {
        this.jmsConsumer = new JmsConsumerConfig();
    }

    @Test
    void testSuccessReceiveMessage() {
        assertDoesNotThrow(() -> jmsConsumer.receiveCreateMessage(new Event(1L, 1L, EventOperation.CREATE, getCurrentTimeInEpoch())));
    }

    @Test
    void testDeleteReceiveMessage() {
        assertDoesNotThrow(() -> jmsConsumer.receiveDeleteMessage(new Event(1L, 1L, EventOperation.CREATE, getCurrentTimeInEpoch())));
    }

}
