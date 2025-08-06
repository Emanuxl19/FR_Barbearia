package com.barbearia.api.util;

import com.barbearia.api.dto.request.BarberRequest;
import com.barbearia.api.dto.response.BarberResponse;
import com.barbearia.api.model.Barber;
import com.barbearia.api.model.Role;
import com.barbearia.api.repository.RoleRepository;
import com.barbearia.api.security.model.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BarberMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public Barber toEntity(BarberRequest request) {
        RoleName roleNameToAssign = request.isAdmin() ? RoleName.ADMIN : RoleName.BARBER;
        Role role = roleRepository.findByName(roleNameToAssign)
                .orElseThrow(() -> new RuntimeException("Papel não encontrado: " + roleNameToAssign));

        return Barber.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .available(request.isAvailable())
                .roles(Set.of(role))
                .build();
    }

    public BarberResponse toResponse(Barber barber) {
        Set<String> roleNames = barber.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
        return BarberResponse.builder()
                .id(barber.getId())
                .name(barber.getName())
                .email(barber.getEmail())
                .available(barber.isAvailable())
                .roles(roleNames)
                .build();
    }

    public void updateEntityFromRequest(BarberRequest request, Barber barber) {
        barber.setName(request.getName());
        barber.setEmail(request.getEmail());
        barber.setAvailable(request.isAvailable());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            barber.setPassword(passwordEncoder.encode(request.getPassword()));
        }
    }
}
