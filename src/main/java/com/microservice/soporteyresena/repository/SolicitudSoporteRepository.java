package com.microservice.soporteyresena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.soporteyresena.model.SolicitudSoporte;

public interface SolicitudSoporteRepository extends JpaRepository<SolicitudSoporte, Long> {
    
}
