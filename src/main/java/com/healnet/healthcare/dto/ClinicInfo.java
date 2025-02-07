package com.healnet.healthcare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClinicInfo {
    private Long id;
    private Long hospitalId;
    private String name;
}
