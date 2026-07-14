package com.microservicio.soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.soporte.model.SolicitudSoporte;

public interface SolicitudSoporteRepository extends JpaRepository<SolicitudSoporte, Long> {
    
}
