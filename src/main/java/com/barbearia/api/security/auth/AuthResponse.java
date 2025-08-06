package com.barbearia.api.security.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO que retorna o token JWT para o cliente após login bem-sucedido.
 */
@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
}
