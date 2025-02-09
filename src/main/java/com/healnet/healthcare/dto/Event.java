package com.healnet.healthcare.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.healnet.healthcare.constants.EventOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Event implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long groupId;
    private Long parentGroupId;
    private String name;
    private EventOperation operation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Long timeStamp;

    public Event() { /* needed for Json serialization */ }

    @JsonCreator
    public Event(
            @JsonProperty("groupId") Long groupId,
            @JsonProperty("parentGroupId") Long parentGroupId,
            @JsonProperty("name") String name,
            @JsonProperty("operation") EventOperation operation,
            @JsonProperty("timeStamp") long timeStamp) {
        this.groupId = groupId;
        this.parentGroupId = parentGroupId;
        this.name = name;
        this.operation = operation;
        this.timeStamp = timeStamp;
    }
}
