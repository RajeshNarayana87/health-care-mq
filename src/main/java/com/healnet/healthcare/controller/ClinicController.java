package com.healnet.healthcare.controller;

import com.healnet.healthcare.dto.ApiResponse;
import com.healnet.healthcare.dto.ClinicInfo;
import com.healnet.healthcare.service.ClinicService;
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
@RequestMapping("/v1/clinic")
@RequiredArgsConstructor
@Log4j2
public class ClinicController {

    private final ClinicService clinicService;

    @GetMapping("/info")
    ResponseEntity<ApiResponse<Object>> getClinicInfo(
            @RequestParam Long clinicId
    ){
        log.info("request received to fetch information for ClinicId: {}", clinicId);
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, clinicService.getClinicInfo(clinicId)));
    }

    @GetMapping("/all/info")
    ResponseEntity<ApiResponse<Object>> getAllClinicInfo(){
        log.info("request received to fetch all Clinic info");
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, clinicService.getAllClinicInfo()));
    }

    @PostMapping("/save")
    ResponseEntity<ApiResponse<Object>> saveClinic(@RequestBody ClinicInfo clinicInfo) {
        log.info("request received to save Clinic info for clinic name: {}", clinicInfo.getName());
        clinicService.saveClinicInfo(clinicInfo);
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, null));
    }
}
