package com.microservice.soporteyresena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.soporteyresena.model.ClasificacionReclamo;
import com.microservice.soporteyresena.service.ClasificacionReclamoService;

@RestController
@RequestMapping("/api/v1/clasificaciones")
public class ClasificacionReclamoController {
    @Autowired
    private ClasificacionReclamoService clasificacionReclamoService;

    @PostMapping("/reclamo/{idReclamo}")
    public ClasificacionReclamo clasificarReclamo(@PathVariable Long idReclamo,@RequestBody ClasificacionReclamo clasificacion) {

        return clasificacionReclamoService.clasificarReclamo(idReclamo,clasificacion);
    }
}
