package com.microservice.soporteyresena.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "evidencias")
public class Evidencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEvidencia;

    @Column(nullable = false)
    private String descripcionArchivo;

    @Column(nullable = false)
    private String tipoArchivo;

    @Column(nullable = false)
    private LocalDate fechaEvidencia;

    @ManyToOne
    @JoinColumn(name = "idReclamo")
    @JsonManagedReference
    private Reclamo reclamo;

}
