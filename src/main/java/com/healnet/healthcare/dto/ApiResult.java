package com.healnet.healthcare.dto;

import com.healnet.healthcare.constants.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult {
    private ApiStatus status;
    private String message;
}
