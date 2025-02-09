package com.healnet.healthcare.service.implmentations;

import com.healnet.healthcare.dto.ClinicInfo;
import com.healnet.healthcare.dto.Event;
import com.healnet.healthcare.entity.Clinic;
import com.healnet.healthcare.entity.Hospital;
import com.healnet.healthcare.entity.Treatment;
import com.healnet.healthcare.exception.UnprocessableEntityException;
import com.healnet.healthcare.repository.ClinicRepository;
import com.healnet.healthcare.repository.HospitalRepository;
import com.healnet.healthcare.repository.TreatmentRepository;
import com.healnet.healthcare.service.ClinicService;
import jakarta.jms.JMSException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Log4j2
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;
    private final HospitalRepository hospitalRepository;
    private final TreatmentRepository treatmentRepository;

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
        clinicRepository.save(new Clinic(clinicInfo.getId(), optionalHospital.get(), clinicInfo.getName(), false));
        log.info("Saved clinic info to DB for clinic: {} successfully", clinicInfo.getName());
    }

    @Override
    public void createGroup(Event event) throws JMSException {
        var optionalClinicInfo = clinicRepository.findById(event.getGroupId());
        if (optionalClinicInfo.isPresent()) {
            var hospitalInfo = checkForHospital(event);
            clinicRepository.save(new Clinic(event.getGroupId(), hospitalInfo, event.getName(), false));
        } else {
            var hospitalInfo = checkForHospital(event);
            clinicRepository.save(new Clinic(event.getGroupId(), hospitalInfo, event.getName(), false));
        }
    }
    @Override
    public void deleteGroup(Event event) throws JMSException {
        var optionalClinicInfo = clinicRepository.findById(event.getGroupId());
        if (optionalClinicInfo.isPresent()) {
            clinicRepository.save(new Clinic(event.getGroupId(), optionalClinicInfo.get().getHospital(), event.getName(), true));
            deleteAllTreatmentInfo(event);
        } else {
           throw new JMSException("Unable to process the Event! invalid group Id Provided");
        }
    }

    private void deleteAllTreatmentInfo(Event event) {
        var treatmentInfo = treatmentRepository.findByClinicId(event.getGroupId());
        treatmentInfo.ifPresent(treatments -> treatments.forEach(this::saveUpdateTreatmentInfo));

    }

    private void saveUpdateTreatmentInfo(Treatment treatment) {
        treatment.setDeleted(true);
        treatmentRepository.save(treatment);
    }

    private Hospital checkForHospital(Event event) throws JMSException {
        var hospitalInfo = hospitalRepository.findById(event.getParentGroupId());
        if (hospitalInfo.isEmpty()) {
            log.error("Exception while processing the event: {}", event.toString());
            throw new JMSException("Unable to process the Event! invalid Parent group Id Provided");
        }
        return hospitalInfo.get();
    }
}
