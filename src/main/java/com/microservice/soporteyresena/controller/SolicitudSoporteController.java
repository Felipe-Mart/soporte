package com.microservice.soporteyresena.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.soporteyresena.model.EstadoSolicitud;
import com.microservice.soporteyresena.model.SolicitudSoporte;
import com.microservice.soporteyresena.service.SolicitudSoporteService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("api/v1/solicitudes")
public class SolicitudSoporteController {
    @Autowired
    private SolicitudSoporteService solicitusSoporteService;

    @PostMapping()
    public SolicitudSoporte postSolicitud(@RequestBody SolicitudSoporte solicitud){
        return solicitusSoporteService.crearSolicitud(solicitud);
    }

    @GetMapping
    public List<SolicitudSoporte> getSolicitus() {
        return solicitusSoporteService.listar();
    }

    @GetMapping("/{id}")
    public SolicitudSoporte getById(@PathVariable long id) {
        return solicitusSoporteService.buscarSolicitudId(id);
    }

    @GetMapping("/estado/{id}")
    public EstadoSolicitud getEstado(@PathVariable Long id) {

        return solicitusSoporteService.estadoSolicitud(id);
    }

    @PutMapping("/{id}")
    public SolicitudSoporte putMethodName(@PathVariable long id, @RequestBody SolicitudSoporte solicitud) {

        return solicitusSoporteService.actualizarSolicitud(id, solicitud);
    }
    
}
