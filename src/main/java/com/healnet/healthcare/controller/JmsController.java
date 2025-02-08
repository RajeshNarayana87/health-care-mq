package com.healnet.healthcare.controller;

import com.healnet.healthcare.dto.ApiResponse;
import com.healnet.healthcare.dto.EventRequest;
import com.healnet.healthcare.service.JmsProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.healnet.healthcare.constants.ApiResultConstants.SUCCESS;

@Log4j2
@RestController
@RequestMapping("/v1/jms")
@RequiredArgsConstructor
public class JmsController {

    private final JmsProducerService jmsProducerService;

    private final JmsListenerEndpointRegistry registry;

    @PostMapping("/sendGroupEvent")
    ResponseEntity<ApiResponse<Object>> sendMessage(@RequestBody EventRequest request) {
        log.info("Publish Event request received for Group id: {} and Operation: {}", request.getGroupId(), request.getOperation());
        jmsProducerService.sendMessage(request);
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, null));
    }

    @GetMapping("/listeners")
    ResponseEntity<ApiResponse<Object>> getActiveListeners() {
        log.info("Get all List of Active Listeners");
        Map<String, Boolean> listenerStatus = new HashMap<>();
        registry.getListenerContainers().forEach(container -> {
            String listenerId = container.toString();
            boolean isRunning = container.isRunning();
            listenerStatus.put(listenerId, isRunning);
        });
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, listenerStatus));
    }

}
