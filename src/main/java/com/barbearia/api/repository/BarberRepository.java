package com.barbearia.api.repository;

import com.barbearia.api.model.Barber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {

    Optional<Barber> findByEmail(String email);

    // Corrigido: O método agora aceita um objeto Pageable para paginação.
    Page<Barber> findByAvailableTrue(Pageable pageable);

    boolean existsByEmail(String email);
}
