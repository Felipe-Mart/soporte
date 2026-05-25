package com.microservice.soporteyresena.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.soporteyresena.model.Evidencia;
import com.microservice.soporteyresena.model.Reclamo;
import com.microservice.soporteyresena.repository.EvidenciaRepository;
import com.microservice.soporteyresena.repository.ReclamoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EvidenciaService {
    @Autowired
    private EvidenciaRepository evidenciaRepository;
    private ReclamoRepository reclamoRepository;


    public Evidencia agregarEvidencia(Long idReclamo,Evidencia evidencia) {

        Reclamo reclamo =reclamoRepository.findById(idReclamo).orElse(null);

        if (reclamo == null) {
            throw new RuntimeException("El reclamo no existe");
        }

        evidencia.setFechaEvidencia(LocalDate.now());
        evidencia.setReclamo(reclamo);

        return evidenciaRepository.save(evidencia);
    }
}
