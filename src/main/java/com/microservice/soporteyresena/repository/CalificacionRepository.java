package com.microservice.soporteyresena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.soporteyresena.model.Calificacion;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long>{
    
}
