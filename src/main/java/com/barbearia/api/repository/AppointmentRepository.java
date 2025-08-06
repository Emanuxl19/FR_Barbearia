package com.barbearia.api.repository;

import com.barbearia.api.model.Appointment;
import com.barbearia.api.model.Barber;
import com.barbearia.api.model.Client;
import com.barbearia.api.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    // Lista agendamentos de um determinado barbeiro
    List<Appointment> findByBarber(Barber barber);

    // Lista agendamentos de um determinado cliente
    List<Appointment> findByClient(Client client);

    // Lista agendamentos futuros de um barbeiro
    List<Appointment> findByBarberAndAppointmentDateTimeAfter(Barber barber, LocalDateTime dateTime);

    // Lista agendamentos por status (ex: PENDING, CONFIRMED)
    List<Appointment> findByStatus(AppointmentStatus status);

    // Verifica se existe conflito de horário com um barbeiro
    boolean existsByBarberAndAppointmentDateTime(Barber barber, LocalDateTime dateTime);
}
