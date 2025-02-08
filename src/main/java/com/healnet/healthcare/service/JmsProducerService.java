package com.healnet.healthcare.service;

import com.healnet.healthcare.dto.EventRequest;

public interface JmsProducerService {

    void sendMessage(EventRequest  eventRequest);
}
