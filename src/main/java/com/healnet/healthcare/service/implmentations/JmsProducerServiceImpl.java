package com.healnet.healthcare.service.implmentations;

import com.healnet.healthcare.service.JmsProducerService;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class JmsProducerServiceImpl implements JmsProducerService {
    private final JmsTemplate jmsTemplate;
    private final Queue queue;

    @Override
    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(queue, message);
        log.info("Sent message: {}", message);
    }
}
