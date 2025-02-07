package com.healnet.healthcare.service.implmentations;

import com.healnet.healthcare.dto.HospitalInfo;
import com.healnet.healthcare.entity.Hospital;
import com.healnet.healthcare.repository.HospitalRepository;
import com.healnet.healthcare.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
@Log4j2
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    @Override
    public List<HospitalInfo> getAllHospitalInfo() {
        log.info("Fetching all hospital info from DB");
        var optionalAllHospitalInfo = hospitalRepository.findByIsNotDeleted();
        log.info("Fetched all hospital info from DB");
        return optionalAllHospitalInfo.map(hospitals -> hospitals.stream()
                .map(hospital -> new HospitalInfo(hospital.getId(), hospital.getHospitalName()))
                .toList()).orElseGet(List::of);
    }

    @Override
    public HospitalInfo getHospitalInfo(Long hospitalId) {
        log.info("Fetching info from DB for hospitalId: {}", hospitalId);
        var optionalHospital = hospitalRepository.findById(hospitalId);
        log.info("Fetched info from DB for hospitalId: {}", hospitalId);
        return optionalHospital.map(hospital -> new HospitalInfo(hospital.getId(), hospital.getHospitalName())).orElse(null);
    }

    @Override
    public void saveHospitalInfo(HospitalInfo hospitalInfo) {
        log.info("Saving Hospital info to DB for hospital: {}", hospitalInfo.getName());
        hospitalRepository.save(new Hospital(hospitalInfo.getName(), false));
        log.info("Saved Hospital info to DB for hospital: {} successfully", hospitalInfo.getName());
    }
}
