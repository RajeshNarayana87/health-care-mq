package com.healnet.healthcare.service.implmentations;

import com.healnet.healthcare.dto.TreatmentInfo;
import com.healnet.healthcare.entity.Treatment;
import com.healnet.healthcare.exception.UnprocessableEntityException;
import com.healnet.healthcare.repository.ClinicRepository;
import com.healnet.healthcare.repository.TreatmentRepository;
import com.healnet.healthcare.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class TreatmentServiceImpl implements TreatmentService {

    private final ClinicRepository clinicRepository;
    private final TreatmentRepository treatmentRepository;

    @Override
    public List<TreatmentInfo> getAllTreatmentInfo() {
        log.info("Fetching all treatment info from DB");
        var optionalAllTreatmentInfo = treatmentRepository.findByIsNotDeleted();
        log.info("Fetched all treatment info from DB");
        return optionalAllTreatmentInfo.map(treatments -> treatments.stream()
                .map(treatment -> new TreatmentInfo(treatment.getId(), treatment.getClinic().getId(), treatment.getTreatmentName()))
                .toList()).orElseGet(List::of);
    }

    @Override
    public TreatmentInfo getTreatmentInfo(Long treatmentId) {
        log.info("Fetching info from DB for treatmentId: {}", treatmentId);
        var optionalTreatment = treatmentRepository.findByIdAndIsDeleted(treatmentId, false);
        log.info("Fetched info from DB for treatmentId: {}", treatmentId);
        return optionalTreatment.map(treatment -> new TreatmentInfo(treatment.getId(), treatment.getClinic().getId(), treatment.getTreatmentName())).orElse(null);
    }

    @Override
    public void saveTreatmentInfo(TreatmentInfo treatmentInfo) {
        log.info("Fetching Clinic info for clinicId: {} to validate", treatmentInfo.getClinicId());
        var optionalClinic = clinicRepository.findById(treatmentInfo.getClinicId());
        if (optionalClinic.isEmpty()) {
            throw new UnprocessableEntityException("Hospital Info Not present for given Hospital Id");
        }
        log.info("Saving Treatment info to DB for Treatment: {}", treatmentInfo.getName());
        treatmentRepository.save(new Treatment(optionalClinic.get(), treatmentInfo.getName(), false));
        log.info("Saved treatment info to DB for clinic: {} successfully", treatmentInfo.getName());
    }
}
