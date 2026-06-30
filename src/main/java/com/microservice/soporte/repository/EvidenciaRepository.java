package com.microservice.soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.soporte.model.Evidencia;

public interface EvidenciaRepository extends JpaRepository<Evidencia,Long> {
    
}
