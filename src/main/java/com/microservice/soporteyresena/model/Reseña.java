package com.microservice.soporteyresena.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reseñas")
public class Reseña {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReseña;

    //Referencias de ids de otros microservicios
    private long idCliente;
    private long idProducto;
    
    @Column(nullable = false)
    private String comentario;

    @Column(nullable = false)
    private LocalDate fecha;
    

}
