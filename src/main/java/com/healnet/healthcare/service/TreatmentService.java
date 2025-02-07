package com.healnet.healthcare.service;

import com.healnet.healthcare.dto.TreatmentInfo;

import java.util.List;

public interface TreatmentService {

    List<TreatmentInfo> getAllTreatmentInfo();

    TreatmentInfo getTreatmentInfo(Long treatmentId);

    void saveTreatmentInfo(TreatmentInfo treatmentInfo);
}
