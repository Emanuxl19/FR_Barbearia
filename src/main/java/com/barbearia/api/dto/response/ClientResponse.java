package com.barbearia.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
}
