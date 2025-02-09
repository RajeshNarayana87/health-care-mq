package com.healnet.healthcare.service.implmentations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healnet.healthcare.constants.EventOperation;
import com.healnet.healthcare.dto.ClinicInfo;
import com.healnet.healthcare.dto.Event;
import com.healnet.healthcare.dto.EventRequest;
import com.healnet.healthcare.exception.UnprocessableEntityException;
import com.healnet.healthcare.service.ClinicService;
import com.healnet.healthcare.service.JmsProducerService;
import jakarta.jms.Message;
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
    private final JmsTemplate jmsTemplate;
    private final ClinicService clinicService;
    private final ObjectMapper objectMapper;

    @Override
    public void sendMessage(EventRequest eventRequest) {
        var clinicInfo = getClinicInfo(eventRequest);
        var event = getEvent(clinicInfo, eventRequest);
        String destination = determineQueue(eventRequest.getOperation());
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
            case EventOperation.CREATE -> "queue.create";
            case EventOperation.DELETE -> "queue.delete";
        };
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
