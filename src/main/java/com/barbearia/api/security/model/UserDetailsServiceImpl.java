package com.barbearia.api.security.model;

import com.barbearia.api.repository.BarberRepository;
import com.barbearia.api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final BarberRepository barberRepository;
    private final ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return barberRepository.findByEmail(email)
                .map(UserDetails.class::cast)
                .orElseGet(() -> clientRepository.findByEmail(email)
                        .map(UserDetails.class::cast)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email)));
    }
}