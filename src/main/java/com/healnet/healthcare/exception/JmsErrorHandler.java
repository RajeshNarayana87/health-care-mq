package com.healnet.healthcare.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Log4j2
@Component
public class JmsErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable th) {
        log.error("JMS Error: {}", th.getMessage(), th);
    }
}

