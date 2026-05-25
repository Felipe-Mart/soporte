package com.microservice.soporteyresena.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reclamos")
public class Reclamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReclamo;

    private long idEmpleado;
    private long idSucursal;

    @OneToOne
    @JoinColumn(name= "idSolocotudSoporte")
    private SolicitudSoporte solicitudSoporte;


    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Evidencia> evidencias;

    @OneToOne(mappedBy = "reclamo", cascade = CascadeType.ALL)
    @JsonManagedReference
    private ClasificacionReclamo clasificacion;

    @OneToOne(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private DevolucionProducto devolucionProducto;
}
