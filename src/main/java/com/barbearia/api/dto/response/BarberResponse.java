package com.barbearia.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder // Adicionada a anotação que faltava
@NoArgsConstructor
@AllArgsConstructor
public class BarberResponse {
    private Long id;
    private String name;
    private String email;
    private boolean available;
    private Set<String> roles;
}
