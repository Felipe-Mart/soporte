package com.microservice.soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.soporte.model.SolicitudSoporte;

public interface SolicitudSoporteRepository extends JpaRepository<SolicitudSoporte, Long> {
    
}
