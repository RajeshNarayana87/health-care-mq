package com.healnet.healthcare.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/***
 * Generic DTO class used in all API response as proper standard Structure
 * @param apiResult used to predict the api result
 * @param data contains the required data after process
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        ApiResult apiResult,
        @JsonProperty("data")
        T data
) {

}
