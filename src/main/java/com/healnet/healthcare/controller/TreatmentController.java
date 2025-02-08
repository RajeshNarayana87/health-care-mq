package com.healnet.healthcare.controller;

import com.healnet.healthcare.dto.ApiResponse;
import com.healnet.healthcare.dto.TreatmentInfo;
import com.healnet.healthcare.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.healnet.healthcare.constants.ApiResultConstants.SUCCESS;

@RestController
@RequestMapping("/v1/treatment")
@RequiredArgsConstructor
@Log4j2
public class TreatmentController {

    private final TreatmentService treatmentService;

    @GetMapping("/info")
    ResponseEntity<ApiResponse<Object>> getTreatmentInfo(
            @RequestParam Long treatmentId
    ){
        log.info("request received to fetch information for treatmentId: {}", treatmentId);
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, treatmentService.getTreatmentInfo(treatmentId)));
    }

    @GetMapping("/all/info")
    ResponseEntity<ApiResponse<Object>> getAllTreatmentInfo(){
        log.info("request received to fetch all treatment info");
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, treatmentService.getAllTreatmentInfo()));
    }

    @PostMapping("/save")
    ResponseEntity<ApiResponse<Object>> saveTreatment(@RequestBody TreatmentInfo treatmentInfo) {
        log.info("request received to save treatment info for treatment name: {}", treatmentInfo.getName());
        treatmentService.saveTreatmentInfo(treatmentInfo);
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, null));
    }
}
