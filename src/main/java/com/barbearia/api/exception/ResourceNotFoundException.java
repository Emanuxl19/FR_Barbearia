package com.barbearia.api.exception;

/**
 * Exceção lançada quando um recurso não é encontrado no sistema.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
