package com.healnet.healthcare.service.implmentations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healnet.healthcare.constants.EventOperation;
import com.healnet.healthcare.dto.ClinicInfo;
import com.healnet.healthcare.dto.Event;
import com.healnet.healthcare.dto.EventRequest;
import com.healnet.healthcare.exception.UnprocessableEntityException;
import com.healnet.healthcare.service.ClinicService;
import com.healnet.healthcare.service.HospitalService;
import com.healnet.healthcare.service.JmsProducerService;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.healnet.healthcare.util.DateUtil.getCurrentTimeInEpoch;


@Service
@RequiredArgsConstructor
@Log4j2
public class JmsProducerServiceImpl implements JmsProducerService {

    @Setter
    @Value("${queue.name.create}")
    private String createQueue;

    @Setter
    @Value("${queue.name.delete}")
    private String deleteQueue;

    private final ClinicService clinicService;
    private final HospitalService hospitalService;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendMessage(EventRequest eventRequest) {
        var treatmentInfo = validateAndGetGroupInfo(eventRequest);
        var event = getEvent(treatmentInfo, eventRequest);
        String destination = determineQueue(eventRequest.getOperation());
        publishMessage(event, destination);
    }

    private void publishMessage(Event event, String destination) {
        log.info("Destination is : {}", destination);
        jmsTemplate.send(destination, session -> {
            String jsonEvent;
            try {
                jsonEvent = objectMapper.writeValueAsString(event);
            } catch (JsonProcessingException e) {
                throw new UnprocessableEntityException(e.getMessage());
            }
            Message message = session.createTextMessage(jsonEvent);
            message.setStringProperty("_type", Event.class.getName());
            return message;
        });
        log.info("Sent event message: {}", event.toString());
    }

    private String determineQueue(EventOperation eventOperation) {
        return switch (eventOperation) {
            case EventOperation.CREATE -> createQueue;
            case EventOperation.DELETE -> deleteQueue;
        };
    }

    private ClinicInfo validateAndGetGroupInfo(EventRequest eventRequest) {
        return switch (eventRequest.getOperation()) {
            case EventOperation.CREATE -> validateCreateOperation(eventRequest);
            case EventOperation.DELETE -> validateDeleteOperation(eventRequest);
        };
    }

    private ClinicInfo validateDeleteOperation(EventRequest eventRequest) {
        var clinicInfo = clinicService.getClinicInfo(eventRequest.getGroupId());
        if (Objects.isNull(clinicInfo)) {
            throw new UnprocessableEntityException("Provided Group Id is invalid");
        }
        checkForHospitalInfo(eventRequest.getParentGroupId());
        return clinicInfo;
    }

    private ClinicInfo validateCreateOperation(EventRequest eventRequest) {
        checkForHospitalInfo(eventRequest.getParentGroupId());
        return new ClinicInfo(eventRequest.getGroupId(), eventRequest.getParentGroupId(), eventRequest.getGroupName());
    }

    private void checkForHospitalInfo(Long parentGroupId) {
        var hospitalInfo = hospitalService.getHospitalInfo(parentGroupId);
        if (Objects.isNull(hospitalInfo)) {
            throw new UnprocessableEntityException("Provided Parent Group Id is invalid");
        }
    }

    private Event getEvent(ClinicInfo clinicInfo, EventRequest eventRequest) {
        return new Event(
                clinicInfo.getId(),
                clinicInfo.getHospitalId(),
                clinicInfo.getName(),
                eventRequest.getOperation(),
                getCurrentTimeInEpoch()
        );
    }
}
