package com.microservice.soporteyresena.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.soporteyresena.model.Reclamo;
import com.microservice.soporteyresena.service.ReclamoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/reclamos")
public class ReclamoController {
    @Autowired
    private ReclamoService reclamoService;


    @PostMapping
    public Reclamo postReseña(@RequestBody Reclamo reclamo) {
        return reclamoService.crearReclamo(reclamo);
    }

    @GetMapping
    public Iterable<Reclamo> listarReclamos() {

        return reclamoService.listarReclamos();
    }

    @GetMapping("/{id}")
    public Reclamo buscarReclamo(@PathVariable Long id) {
        return reclamoService.buscarReclamoId(id);
    }

    @PutMapping("/finalizar/{id}")
    public Reclamo finalizarReclamo(@PathVariable Long id) {
        return reclamoService.finalizarReclamo(id);
    }

    @GetMapping("/estado/{id}")
    public String consultarEstadoSolicitud(@PathVariable Long id) {

        return reclamoService.consultarEstado(id);
    }

    @PutMapping("/{id}/sucursal/{idSucursal}")
    public Reclamo asignarSucursal(@PathVariable Long id,@PathVariable Long idSucursal) {
        return reclamoService.asignarSucursal(id, idSucursal);
    }
}
