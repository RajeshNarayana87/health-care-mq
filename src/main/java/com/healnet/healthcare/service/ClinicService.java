package com.healnet.healthcare.service;

import com.healnet.healthcare.dto.ClinicInfo;

import java.util.List;

public interface ClinicService {

    List<ClinicInfo> getAllClinicInfo();

    ClinicInfo getClinicInfo(Long clinicId);

    void saveClinicInfo(ClinicInfo clinicInfo);
}
