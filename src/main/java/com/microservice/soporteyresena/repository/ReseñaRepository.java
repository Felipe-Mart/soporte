package com.microservice.soporteyresena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.soporteyresena.model.Reseña;

public interface ReseñaRepository extends JpaRepository<Reseña,Long>{
    
}
