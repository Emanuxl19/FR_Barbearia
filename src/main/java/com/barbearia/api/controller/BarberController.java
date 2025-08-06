package com.barbearia.api.controller;

import com.barbearia.api.dto.request.BarberRequest;
import com.barbearia.api.dto.response.BarberResponse;
import com.barbearia.api.service.BarberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/barbers")
@RequiredArgsConstructor
public class BarberController {

    private final BarberService barberService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BarberResponse> create(@Valid @RequestBody BarberRequest request) {
        BarberResponse response = barberService.create(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<BarberResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(barberService.findAll(pageable));
    }

    @GetMapping("/available")
    public ResponseEntity<Page<BarberResponse>> findAvailable(Pageable pageable) {
        return ResponseEntity.ok(barberService.findAvailable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(barberService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BarberResponse> update(@PathVariable Long id, @Valid @RequestBody BarberRequest request) {
        return ResponseEntity.ok(barberService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        barberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
