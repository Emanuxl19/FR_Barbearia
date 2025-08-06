package com.barbearia.api.mapper;

import com.barbearia.api.dto.response.AppointmentResponse;
import com.barbearia.api.model.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentResponse toResponse(Appointment entity) {
        return AppointmentResponse.builder()
                .id(entity.getId())
                .appointmentDateTime(entity.getAppointmentDateTime())
                .status(entity.getStatus())
                .clientId(entity.getClient().getId())
                .clientName(entity.getClient().getName())
                .barberId(entity.getBarber().getId())
                .barberName(entity.getBarber().getName())
                .build();
    }
}
