package com.healnet.healthcare.config;

import com.healnet.healthcare.dto.Event;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/***
 *this component handles the DLQ listener
 */
@Component
@Log4j2
public class DeadLetterQueueConsumer {
    @JmsListener(destination = "DLQ.EVENT.CREATE")
    public void receiveDLQCreateMessage(Event event) {
        log.warn("Message Received from DLQ: {}", event);
        // Here, we could log, alert, or retry processing.
    }

    @JmsListener(destination = "DLQ.EVENT.DELETE")
    public void receiveDLQDeleteMessage(Event event) {
        log.warn("Message Received from DLQ: {}", event);
        // Here, we could log, alert, or retry processing.
    }
}

