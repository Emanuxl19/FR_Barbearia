package com.barbearia.api.dto.response;

import com.barbearia.api.model.AppointmentStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentResponse {

    private Long id;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status; // Corrigido
    private Long clientId;
    private String clientName;
    private Long barberId;
    private String barberName;
}
