package com.healnet.healthcare.controller;

import com.healnet.healthcare.dto.ApiResponse;
import com.healnet.healthcare.dto.HospitalInfo;
import com.healnet.healthcare.service.HospitalService;
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
@RequestMapping("/v1/hospital")
@RequiredArgsConstructor
@Log4j2
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping("/info")
    ResponseEntity<ApiResponse<Object>> getHospitalInfo(
            @RequestParam Long hospitalId
    ){
        log.info("request received to fetch information for hospitalId: {}", hospitalId);
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, hospitalService.getHospitalInfo(hospitalId)));
    }

    @GetMapping("/all/info")
    ResponseEntity<ApiResponse<Object>> getAllHospitalInfo(){
        log.info("request received to fetch all hospital info");
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, hospitalService.getAllHospitalInfo()));
    }

    @PostMapping("/save")
    ResponseEntity<ApiResponse<Object>> saveClinic(@RequestBody HospitalInfo hospitalInfo) {
        log.info("request received to save Hospital info for hospital Name: {}", hospitalInfo.getName());
        hospitalService.saveHospitalInfo(hospitalInfo);
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, null));
    }
}
