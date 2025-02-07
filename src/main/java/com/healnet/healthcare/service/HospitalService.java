package com.healnet.healthcare.service;

import com.healnet.healthcare.dto.HospitalInfo;

import java.util.List;

public interface HospitalService {

    List<HospitalInfo> getAllHospitalInfo();

    HospitalInfo getHospitalInfo(Long hospitalId);

    void saveHospitalInfo(HospitalInfo hospitalInfo);
}
