package com.microservice.soporteyresena.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.soporteyresena.model.Reseña;
import com.microservice.soporteyresena.repository.ReseñaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReseñaService {
    @Autowired
    private ReseñaRepository reseñaRepository;

    public List<Reseña> listarReseñas() {
        return reseñaRepository.findAll();
    }

    public Reseña crearReseña(Reseña reseña) {
        reseña.setFecha(LocalDate.now());
        return reseñaRepository.save(reseña);
    }

    public Reseña buscarReseñaId(Long id) {
        return reseñaRepository.findById(id).orElse(null);
    }

    public void eliminarReseña(long id){
        reseñaRepository.deleteById(id);
    }

}
