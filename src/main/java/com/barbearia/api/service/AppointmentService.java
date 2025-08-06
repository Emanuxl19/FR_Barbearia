package com.barbearia.api.service;

import com.barbearia.api.dto.request.AppointmentRequest;
import com.barbearia.api.dto.response.AppointmentResponse;
import com.barbearia.api.exception.BusinessException;
import com.barbearia.api.exception.ResourceNotFoundException;
import com.barbearia.api.mapper.AppointmentMapper;
import com.barbearia.api.model.*;
import com.barbearia.api.repository.AppointmentRepository;
import com.barbearia.api.repository.BarberRepository;
import com.barbearia.api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;
    private final ClientRepository clientRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public AppointmentResponse create(AppointmentRequest request) {
        Barber barber = barberRepository.findById(request.getBarberId())
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + request.getBarberId()));

        if (!barber.isAvailable()) {
            throw new BusinessException("O barbeiro selecionado não está disponível.");
        }

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + request.getClientId()));

        if (appointmentRepository.existsByBarberAndAppointmentDateTime(barber, request.getAppointmentDateTime())) {
            throw new BusinessException("O barbeiro já possui um agendamento para este horário.");
        }

        Appointment appointment = Appointment.builder()
                .appointmentDateTime(request.getAppointmentDateTime())
                .barber(barber)
                .client(client)
                .status(AppointmentStatus.PENDING)
                .build();

        Appointment saved = appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<AppointmentResponse> findAll(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse findById(Long id) {
        return appointmentRepository.findById(id)
                .map(appointmentMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com o ID: " + id));
    }

    @Transactional
    public AppointmentResponse updateStatus(Long id, AppointmentStatus newStatus) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com o ID: " + id));
        appointment.setStatus(newStatus);
        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não foi possível encontrar um agendamento com o ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean isOwner(Long appointmentId, String username) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> appointment.getClient().getEmail().equals(username) ||
                        appointment.getBarber().getEmail().equals(username))
                .orElse(false);
    }
}
