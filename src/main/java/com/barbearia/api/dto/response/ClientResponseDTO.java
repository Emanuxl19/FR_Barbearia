package com.barbearia.api.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de resposta para informações públicas do cliente.
 */
@Data
@AllArgsConstructor
public class ClientResponseDTO {

    private Long id;
    private String name;
    private String email;
}