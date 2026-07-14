package com.microservicio.soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.soporte.model.Evidencia;

public interface EvidenciaRepository extends JpaRepository<Evidencia,Long> {
    
}
