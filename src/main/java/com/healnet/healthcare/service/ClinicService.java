package com.healnet.healthcare.service;

import com.healnet.healthcare.dto.ClinicInfo;
import com.healnet.healthcare.dto.Event;
import jakarta.jms.JMSException;

import java.util.List;

public interface ClinicService {

    List<ClinicInfo> getAllClinicInfo();

    ClinicInfo getClinicInfo(Long clinicId);

    void saveClinicInfo(ClinicInfo clinicInfo);

    void createGroup(Event event) throws JMSException;

    void deleteGroup(Event event) throws JMSException;
}
