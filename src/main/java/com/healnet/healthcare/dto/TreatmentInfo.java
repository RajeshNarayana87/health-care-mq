package com.healnet.healthcare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TreatmentInfo {
    private Long id;
    private Long clinicId;
    private String name;
}
