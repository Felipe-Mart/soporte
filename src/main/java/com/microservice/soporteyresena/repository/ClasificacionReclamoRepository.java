package com.microservice.soporteyresena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.soporteyresena.model.ClasificacionReclamo;

public interface ClasificacionReclamoRepository extends JpaRepository<ClasificacionReclamo, Long> {
    
}
