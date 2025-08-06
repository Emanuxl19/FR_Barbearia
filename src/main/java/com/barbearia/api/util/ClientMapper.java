package com.barbearia.api.util;

import com.barbearia.api.dto.request.ClientRequest;
import com.barbearia.api.dto.response.ClientResponse;
import com.barbearia.api.model.Client;
import com.barbearia.api.model.Role;
import com.barbearia.api.repository.RoleRepository;
import com.barbearia.api.security.model.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public Client toEntity(ClientRequest request) {
        Role role = roleRepository.findByName(RoleName.CLIENT)
                .orElseThrow(() -> new RuntimeException("Papel não encontrado: " + RoleName.CLIENT));

        return Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .roles(Set.of(role))
                .build();
    }

    public ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .build();
    }
}
