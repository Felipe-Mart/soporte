package com.microservice.soporteyresena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.soporteyresena.model.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo,Long> {
    
}
