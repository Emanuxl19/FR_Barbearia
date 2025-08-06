package com.barbearia.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BarberRequest {
    // Nome completo do barbeiro
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100)
    private String name;

    //E-mail para acesso
    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    // Senha do barbeiro (não será exposta na resposta)
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;

    // Status de disponibilidade inicial
    private boolean available = true;

    // Define se o barbeiro é administrador
    private boolean admin = false;
}
