package com.microservice.soporteyresena.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devolucionproductos")
public class DevolucionProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDevolucion;

    // Referencia id de otra clase de otro microservicio
    private long idProducto;


    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoDevolucion estado;

    @Column(nullable = false)
    private LocalDate fechaDevolucion;
    
    @ManyToOne
    @JoinColumn(name = "idReclamo")
    private Reclamo reclamo;
}
