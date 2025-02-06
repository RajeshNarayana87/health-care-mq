package com.healnet.healthcare.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.healnet.healthcare.dto.ApiResult;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        ApiResult resultInfo,
        @JsonProperty("data")
        T data
) {

}
