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

import com.microservice.soporteyresena.model.Calificacion;
import com.microservice.soporteyresena.service.CalificacionService;

@RestController
@RequestMapping("/api/v1/calificaciones")
public class CalificacionController {
    @Autowired
    private CalificacionService calificacionService;

    @GetMapping
    public List<Calificacion> listarCalificaciones() {

        return calificacionService.listarCalificaciones();
    }

    @GetMapping("/{id}")
    public Calificacion buscarCalificacion(
            @PathVariable Long id) {

        return calificacionService.buscarCalificacionId(id);
    }

    @PostMapping
    public Calificacion agregarCalificacion(@RequestBody Calificacion calificacion) {

        return calificacionService.generarCalificacion(calificacion);
    }

    @DeleteMapping("/{id}")
    public void eliminarCalificacion(@PathVariable Long id) {
        calificacionService.eliminarCalificacion(id);
    }

}
