package com.microservice.soporteyresena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.soporteyresena.model.DevolucionProducto;

public interface DevolucionProductoRepository extends JpaRepository<DevolucionProducto, Long> {
    
}
