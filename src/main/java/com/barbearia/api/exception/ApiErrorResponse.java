package com.barbearia.api.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Estrutura padronizada para respostas de erro.
 */
@Getter
@Builder
public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> details;   // Erros de validação ou mensagens adicionais
    private String errorCode;       // Código interno para facilitar debug e internacionalização
}
