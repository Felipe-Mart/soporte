package com.microservicio.soporte.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private long idProducto;
    private String nombProducto;
    private Double precio;
}
