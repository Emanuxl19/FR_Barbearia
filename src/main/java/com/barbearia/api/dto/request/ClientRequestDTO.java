package com.barbearia.api.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO usado para cadastro de novos clientes.
 */
@Data
public class ClientRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    private String password;
}