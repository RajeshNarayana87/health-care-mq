package com.healnet.healthcare.controller;

import com.healnet.healthcare.config.ApiResponse;
import com.healnet.healthcare.service.JmsProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.healnet.healthcare.constants.ApiResultConstants.SUCCESS;

@RestController
@RequestMapping("/api/jms")
@RequiredArgsConstructor
public class JmsController {

    private final JmsProducerService jmsProducerService;

    @PostMapping("/send")
    ResponseEntity<ApiResponse<Object>> sendMessage(@RequestParam String message) {
        jmsProducerService.sendMessage(message);
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, null));
    }
}
