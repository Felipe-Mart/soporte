package com.microservice.soporteyresena.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clasificacionesreclamo")
public class ClasificacionReclamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idClasificacion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoriaReclamo categoria;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PrioriedadReclamo prioridad;

    @Column(nullable = false)
    private String observacion;

    @OneToOne
    @JoinColumn(name = "idReclamo")
    @JsonBackReference
    private Reclamo reclamo;


}
