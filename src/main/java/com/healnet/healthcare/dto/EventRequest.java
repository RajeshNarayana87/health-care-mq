package com.healnet.healthcare.dto;

import com.healnet.healthcare.constants.EventOperation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRequest {
    private Long groupId;
    private EventOperation operation;
}
