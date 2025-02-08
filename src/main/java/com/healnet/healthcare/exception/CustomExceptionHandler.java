package com.healnet.healthcare.exception;

import com.healnet.healthcare.dto.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.healnet.healthcare.constants.ApiResultConstants.FAILURE;
import static com.healnet.healthcare.constants.ApiResultConstants.INTERNAL_SERVER_ERROR;

@Log4j2
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return ResponseEntity.badRequest().body(new ApiResponse<>(FAILURE, errorResponse));
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> internalServerErrorHandler(Exception ex) {
        log.error("Error Message: {}", ex.getMessage(), ex);
        log.error(ex.getStackTrace());
        return new ApiResponse<>(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = {UnprocessableEntityException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiResponse<Object> unprocessableEntityExceptionHandler(UnprocessableEntityException ex) {
        log.error("Error Message: {}", ex.getMessage(), ex);
        log.error(ex.getStackTrace());
        return new ApiResponse<>(FAILURE, ex.getMessage());
    }
}

