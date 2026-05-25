package com.microservice.soporteyresena.model;


import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "solicitudes")
public class SolicitudSoporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSolicitud;
    
    //Referencias de ids de otras clases en otros microservicios
    private long idCliente;
    private long idProducto;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSolicitud tipoSolicitud;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @Column(nullable = false)
    private LocalDate fechaSolicitud;


}
