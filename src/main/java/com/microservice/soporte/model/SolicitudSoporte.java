package com.microservice.soporte.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "solicitudes_soporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    @Column(nullable = false)
    private Long idCliente;

    @Column(nullable = false)
    private Long idProducto;

    private Long idEmpleado;

    private Long idSucursal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSolicitud tipoSolicitud;

    @Enumerated(EnumType.STRING)
    private CategoriaReclamo categoriaReclamo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "solicitudSoporte",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Evidencia> evidencias = new ArrayList<>();
}