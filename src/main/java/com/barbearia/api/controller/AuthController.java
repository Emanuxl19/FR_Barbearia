package com.barbearia.api.controller;

import com.barbearia.api.security.auth.AuthRequest;
import com.barbearia.api.dto.request.ClientRequest;
import com.barbearia.api.security.auth.AuthResponse;
import com.barbearia.api.dto.response.ClientResponse;
import com.barbearia.api.security.auth.AuthService;
import com.barbearia.api.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ClientService clientService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    // O registro de cliente é público e faz mais sentido estar aqui.
    @PostMapping("/register")
    public ResponseEntity<ClientResponse> register(@Valid @RequestBody ClientRequest request) {
        ClientResponse response = clientService.register(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/clients/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
