package com.healnet.healthcare.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.healnet.healthcare.constants.EventOperation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Event implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long groupId;
    private Long parentGroupId;
    private EventOperation operation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Long timeStamp;

    @Override
    public String toString() {
        return "Event{" +
                "groupId=" + groupId +
                ", parentGroupId=" + parentGroupId +
                ", operation=" + operation +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
