package com.barbearia.api.service;

import com.barbearia.api.dto.request.BarberRequest;
import com.barbearia.api.dto.response.BarberResponse;
import com.barbearia.api.exception.BusinessException;
import com.barbearia.api.exception.ResourceNotFoundException;
import com.barbearia.api.util.BarberMapper;
import com.barbearia.api.model.Barber;
import com.barbearia.api.repository.BarberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BarberService {

    private final BarberRepository barberRepository;
    private final BarberMapper barberMapper;

    @Transactional
    public BarberResponse create(BarberRequest request) {
        if (barberRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("O e-mail informado já está em uso.");
        }
        Barber barber = barberMapper.toEntity(request);
        Barber savedBarber = barberRepository.save(barber);
        return barberMapper.toResponse(savedBarber);
    }

    @Transactional(readOnly = true)
    public Page<BarberResponse> findAll(Pageable pageable) {
        return barberRepository.findAll(pageable).map(barberMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<BarberResponse> findAvailable(Pageable pageable) {
        // Corrigido: Chama o método correto do repositório que aceita paginação.
        return barberRepository.findByAvailableTrue(pageable).map(barberMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public BarberResponse findById(Long id) {
        return barberRepository.findById(id)
                .map(barberMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + id));
    }

    @Transactional
    public BarberResponse update(Long id, BarberRequest request) {
        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + id));

        if (!barber.getEmail().equals(request.getEmail()) && barberRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("O e-mail informado já está em uso por outro usuário.");
        }

        barberMapper.updateEntityFromRequest(request, barber);
        Barber updatedBarber = barberRepository.save(barber);
        return barberMapper.toResponse(updatedBarber);
    }

    @Transactional
    public void delete(Long id) {
        if (!barberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não foi possível encontrar um barbeiro com o ID: " + id);
        }
        barberRepository.deleteById(id);
    }
}
