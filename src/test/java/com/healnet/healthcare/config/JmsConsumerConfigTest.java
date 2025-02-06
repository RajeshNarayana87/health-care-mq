package com.healnet.healthcare.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        String message = "Test Message";

        assertDoesNotThrow(() -> jmsConsumer.receiveMessage(message));
    }

    @Test
    void testFailureReceiveMessage() {
        String message = "error";

        assertThrows(RuntimeException.class, () -> jmsConsumer.receiveMessage(message));
    }

}
