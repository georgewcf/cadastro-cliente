package com.api.cadastrocliente.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {

    @NotBlank
    private String clienteNome;

    @NotBlank
    private Long id;
}
