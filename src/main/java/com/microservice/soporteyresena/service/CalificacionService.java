package com.microservice.soporteyresena.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.soporteyresena.model.Calificacion;
import com.microservice.soporteyresena.repository.CalificacionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CalificacionService {
    @Autowired
    private CalificacionRepository calificacionRepository;

    public List<Calificacion> listarCalificaciones() {
        return calificacionRepository.findAll();
    }

    public Calificacion generarCalificacion(Calificacion calificacion) {
        calificacion.setFechaCalificacion(LocalDate.now());
        return calificacionRepository.save(calificacion);
    }

    public Calificacion buscarCalificacionId(Long id) {
        return calificacionRepository.findById(id).orElse(null);
    }

    public void eliminarCalificacion(Long id) {
        calificacionRepository.deleteById(id);
    }
}
