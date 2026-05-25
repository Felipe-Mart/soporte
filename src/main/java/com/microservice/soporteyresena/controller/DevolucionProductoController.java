package com.microservice.soporteyresena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.soporteyresena.model.DevolucionProducto;
import com.microservice.soporteyresena.model.EstadoDevolucion;
import com.microservice.soporteyresena.service.DevolucionProductoService;

@RestController
@RequestMapping("/api/v1/devoluciones")
public class DevolucionProductoController {
    @Autowired
    private DevolucionProductoService devolucionProductoService;

    @PostMapping("/crear")
    public DevolucionProducto crearDevolucion(Long idReclamo,
            @RequestBody DevolucionProducto devolucion) {

        return devolucionProductoService.crearDevolucion(idReclamo,devolucion);
    }

    @GetMapping("/estado/{id}")
    public EstadoDevolucion consultarEstado(@PathVariable Long id) {

        return devolucionProductoService.consultarEstado(id);
    }

    @PutMapping("/aprobar/{id}")
    public DevolucionProducto aprobarDevolucion(@PathVariable Long id) {

        return devolucionProductoService.aprobarDevolucion(id);
    }

    @PutMapping("/rechazar/{id}")
    public DevolucionProducto rechazarDevolucion(@PathVariable Long id) {

        return devolucionProductoService.rechazarDevolucion(id);
    }
    
}
