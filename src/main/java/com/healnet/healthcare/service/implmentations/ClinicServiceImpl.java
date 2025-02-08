package com.healnet.healthcare.service.implmentations;

import com.healnet.healthcare.dto.ClinicInfo;
import com.healnet.healthcare.entity.Clinic;
import com.healnet.healthcare.exception.UnprocessableEntityException;
import com.healnet.healthcare.repository.ClinicRepository;
import com.healnet.healthcare.repository.HospitalRepository;
import com.healnet.healthcare.service.ClinicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public List<ClinicInfo> getAllClinicInfo() {
        log.info("Fetching all clinic info from DB");
        var optionalAllClinicInfo = clinicRepository.findByIsNotDeleted();
        log.info("Fetched all clinic info from DB");
        return optionalAllClinicInfo.map(clinics -> clinics.stream()
                .map(clinic -> new ClinicInfo(clinic.getId(), clinic.getHospital().getId(), clinic.getClinicName()))
                .toList()).orElseGet(List::of);
    }

    @Override
    public ClinicInfo getClinicInfo(Long clinicId) {
        log.info("Fetching info from DB for clinicId: {}", clinicId);
        var optionalClinic = clinicRepository.findByIdAndIsDeleted(clinicId, false);
        log.info("Fetched info from DB for clinicId: {}", clinicId);
        return optionalClinic.map(clinic -> new ClinicInfo(clinic.getId(), clinic.getHospital().getId(), clinic.getClinicName())).orElse(null);
    }

    @Override
    public void saveClinicInfo(ClinicInfo clinicInfo) {
        log.info("Fetching hospital info for hospitalId: {} to validate", clinicInfo.getHospitalId());
        var optionalHospital = hospitalRepository.findById(clinicInfo.getHospitalId());
        if (optionalHospital.isEmpty()) {
            throw new UnprocessableEntityException("Hospital Info Not present for given Hospital Id");
        }
        log.info("Saving Clinic info to DB for Clinic: {}", clinicInfo.getName());
        clinicRepository.save(new Clinic(optionalHospital.get(), clinicInfo.getName(), false));
        log.info("Saved clinic info to DB for clinic: {} successfully", clinicInfo.getName());
    }
}
