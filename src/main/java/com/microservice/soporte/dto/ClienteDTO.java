package com.microservice.soporte.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long idCliente;
    private String nombre;
    private String rut;
    private String email;
}
