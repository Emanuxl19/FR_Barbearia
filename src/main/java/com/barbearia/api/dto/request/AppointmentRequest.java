package com.barbearia.api.dto.request;

import jakarta.validation.constraints.*;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {

    // Data e hora do agendamento
    @NotNull(message = "A data e hora do agendamento são obrigatórias")
    private LocalDateTime appointmentDateTime;

    // ID do barbeiro responsável
    @NotNull(message = "O barbeiro é obrigatório")
    private Long barberId;

    // ID do cliente que está agendando
    @NotNull(message = "O cliente é obrigatório")
    private Long clientId;
}
