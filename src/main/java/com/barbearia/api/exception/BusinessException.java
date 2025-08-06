package com.barbearia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção customizada para representar violações de regras de negócio.
 * É mapeada para o status HTTP 400 (Bad Request) pelo GlobalExceptionHandler.
 * Use esta exceção quando uma operação não pode ser concluída devido a uma
 * condição de negócio inválida (ex: e-mail duplicado, conflito de agendamento).
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {

    private final String errorCode;

    /**
     * Construtor que aceita apenas a mensagem.
     * Ideal para uso simples sem código de erro.
     *
     * @param message Mensagem explicando a violação da regra de negócio.
     */
    public BusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_RULE_VIOLATION"; // padrão genérico
    }

    /**
     * Construtor com mensagem e código de erro customizado.
     *
     * @param message   Mensagem explicativa.
     * @param errorCode Código customizado (ex: EMAIL_ALREADY_EXISTS).
     */
    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
