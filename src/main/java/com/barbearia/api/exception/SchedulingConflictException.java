package com.barbearia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando há um conflito de agendamento (ex: mesmo horário com o mesmo barbeiro).
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class SchedulingConflictException extends RuntimeException {

    public SchedulingConflictException(String message) {
        super(message);
    }
}
