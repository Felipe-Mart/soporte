package com.microservice.soporteyresena.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.soporteyresena.model.Reseña;
import com.microservice.soporteyresena.service.ReseñaService;

@RestController
@RequestMapping("/api/v1/reseñas")
public class ReseñaController {
    @Autowired
    private ReseñaService reseñaService;

    @GetMapping
    public List<Reseña> getReseñas() {
        return reseñaService.listarReseñas();
    }

    @PostMapping
    public Reseña postReseña(@RequestBody Reseña reseña) {
        return reseñaService.crearReseña(reseña);
    }

     @GetMapping("/{id}")
    public Reseña getReseñaId(@PathVariable long id) {
        return reseñaService.buscarReseñaId(id);
    }

    @DeleteMapping
    public void deleteReseña(@PathVariable long id){
        reseñaService.eliminarReseña(id);
    }
}
