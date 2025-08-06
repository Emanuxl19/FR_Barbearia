package com.barbearia.api.service;
import com.barbearia.api.dto.request.ClientRequest;
import com.barbearia.api.dto.response.ClientResponse;
import com.barbearia.api.exception.BusinessException;
import com.barbearia.api.exception.ResourceNotFoundException;
import com.barbearia.api.util.ClientMapper;
import com.barbearia.api.model.Client;
import com.barbearia.api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public ClientResponse register(ClientRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("O e-mail informado já está em uso.");
        }
        Client client = clientMapper.toEntity(request);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponse(savedClient);
    }

    @Transactional(readOnly = true)
    public Page<ClientResponse> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    @Transactional
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não foi possível encontrar um cliente com o ID: " + id);
        }
        clientRepository.deleteById(id);
    }
}