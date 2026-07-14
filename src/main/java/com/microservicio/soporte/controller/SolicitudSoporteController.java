package com.microservicio.soporte.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservicio.soporte.model.EstadoSolicitud;
import com.microservicio.soporte.model.Evidencia;
import com.microservicio.soporte.model.SolicitudSoporte;
import com.microservicio.soporte.service.SolicitudSoporteService;

@RestController
@RequestMapping("/api/v1/soporte")
public class SolicitudSoporteController {

    @Autowired
    private SolicitudSoporteService solicitudService;

    @PostMapping
    public ResponseEntity<?> crearSolicitud(
            @Valid @RequestBody SolicitudSoporte solicitud) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(solicitudService.crearSolicitud(solicitud));
    }

    @GetMapping
    public ResponseEntity<?> listarSolicitudes() {

        return ResponseEntity.ok(
                solicitudService.listarSolicitudes());
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> buscarSolicitud(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudService.buscarSolicitud(id));
    }

    @PutMapping("{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoSolicitud estado) {

        return ResponseEntity.ok(
                solicitudService.actualizarEstado(id, estado));
    }

    @PutMapping("{id}/finalizar")
    public ResponseEntity<?> finalizarSolicitud(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudService.finalizarSolicitud(id));
    }

    @PutMapping("/{id}/asignar-sucursal")
    public ResponseEntity<?> asignarSucursal(
            @PathVariable Long id,
            @RequestParam Long idEmpleado,
            @RequestParam Long idSucursal) {

        return ResponseEntity.ok(
                solicitudService.asignarSucursal(
                        id,
                        idEmpleado,
                        idSucursal));
    }

    @PostMapping("/{id}/evidencias")
    public ResponseEntity<?> agregarEvidencia(
            @PathVariable Long id,
            @Valid @RequestBody Evidencia evidencia) {

        return ResponseEntity.ok(
                solicitudService.agregarEvidencia(
                        id,
                        evidencia));
    }

    @PutMapping("/{id}/devolucion")
    public ResponseEntity<?> procesarDevolucion(
            @PathVariable Long id) {

        solicitudService.procesarDevolucion(id);

        return ResponseEntity.ok(
                "Devolución procesada correctamente");
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarSolicitud(
            @PathVariable Long id) {

        solicitudService.eliminarSolicitud(id);

        return ResponseEntity.ok(
                "Solicitud eliminada correctamente");
    }
}