package com.microservice.soporteyresena.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.soporteyresena.model.ClasificacionReclamo;
import com.microservice.soporteyresena.model.Reclamo;
import com.microservice.soporteyresena.repository.ClasificacionReclamoRepository;
import com.microservice.soporteyresena.repository.ReclamoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClasificacionReclamoService {
    @Autowired
    private ClasificacionReclamoRepository clasificacionRepository;
    private ReclamoRepository reclamoRepository;


    public ClasificacionReclamo clasificarReclamo(
        Long idReclamo,
        ClasificacionReclamo clasificacion) {

        Reclamo reclamo =reclamoRepository.findById(idReclamo).orElse(null);

        if (reclamo == null) {
            throw new RuntimeException("El reclamo no existe");
        }
        clasificacion.setReclamo(reclamo);

        return clasificacionRepository.save(clasificacion);
    }
}
