package com.microservice.soporteyresena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.soporteyresena.model.Evidencia;

public interface EvidenciaRepository extends JpaRepository<Evidencia,Long> {
    
}
