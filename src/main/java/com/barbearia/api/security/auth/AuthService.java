package com.barbearia.api.security.auth;

import com.barbearia.api.security.auth.AuthRequest;
import com.barbearia.api.security.auth.AuthResponse;
import com.barbearia.api.security.model.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse authenticate(AuthRequest request) {
        // Delega a autenticação para o AuthenticationManager do Spring Security.
        // Ele usará o UserDetailsServiceImpl e o PasswordEncoder configurados.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Se a autenticação for bem-sucedida, gera o token.
        var user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
