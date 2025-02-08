package com.healnet.healthcare.service.implmentations;

import com.healnet.healthcare.dto.ClinicInfo;
import com.healnet.healthcare.dto.Event;
import com.healnet.healthcare.dto.EventRequest;
import com.healnet.healthcare.exception.UnprocessableEntityException;
import com.healnet.healthcare.service.ClinicService;
import com.healnet.healthcare.service.JmsProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.healnet.healthcare.util.DateUtil.getCurrentTimeInEpoch;


@Service
@RequiredArgsConstructor
@Log4j2
public class JmsProducerServiceImpl implements JmsProducerService {
    private static final String EVENT_SUFFIX = "EVENT.";
    private final JmsTemplate jmsTemplate;
    private final ClinicService clinicService;

    @Override
    public void sendMessage(EventRequest eventRequest) {
        var clinicInfo = getClinicInfo(eventRequest);
        var event = getEvent(clinicInfo, eventRequest);
        String destination = EVENT_SUFFIX + eventRequest.getOperation().name();
        log.info("Destination is : {}", destination);
        jmsTemplate.convertAndSend(destination, event);
        log.info("Sent event message: {}", event.toString());
    }

    private ClinicInfo getClinicInfo(EventRequest eventRequest) {
        var clinicInfo = clinicService.getClinicInfo(eventRequest.getGroupId());
        if (Objects.isNull(clinicInfo)) {
            throw new UnprocessableEntityException("Provided Group Id is invalid");
        }
        return clinicInfo;
    }

    private Event getEvent(ClinicInfo clinicInfo, EventRequest eventRequest) {
        return new Event(
                clinicInfo.getId(),
                clinicInfo.getHospitalId(),
                eventRequest.getOperation(),
                getCurrentTimeInEpoch()
        );
    }
}
