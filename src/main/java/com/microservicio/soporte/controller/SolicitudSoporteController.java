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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/soporte")
@Tag(name = "Soporte", description = "Operaciones relacionadas con la gestión de solicitudes de soporte, evidencias, estados y devoluciones.")
public class SolicitudSoporteController {

    @Autowired
    private SolicitudSoporteService solicitudService;

    @PostMapping
    @Operation(summary = "Crear solicitud de soporte", description = "Registra una nueva solicitud de soporte en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos o incompletos")
    })
    public ResponseEntity<?> crearSolicitud(
            @Valid @RequestBody SolicitudSoporte solicitud) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(solicitudService.crearSolicitud(solicitud));
    }

    @GetMapping
    @Operation(summary = "Listar solicitudes", description = "Obtiene una lista de todas las solicitudes de soporte registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de solicitudes obtenida correctamente")
    })
    public ResponseEntity<?> listarSolicitudes() {

        return ResponseEntity.ok(
                solicitudService.listarSolicitudes());
    }

    @GetMapping("buscar/{id}")
    @Operation(summary = "Buscar solicitud por ID", description = "Obtiene los detalles de una solicitud de soporte específica mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<?> buscarSolicitud(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudService.buscarSolicitud(id));
    }

    @PutMapping("{id}/estado")
    @Operation(summary = "Actualizar estado de la solicitud", description = "Modifica el estado actual de una solicitud de soporte.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado proporcionado inválido"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada para actualizar")
    })
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoSolicitud estado) {

        return ResponseEntity.ok(
                solicitudService.actualizarEstado(id, estado));
    }

    @PutMapping("{id}/finalizar")
    @Operation(summary = "Finalizar solicitud", description = "Marca una solicitud de soporte como finalizada o resuelta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud finalizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada para finalizar")
    })
    public ResponseEntity<?> finalizarSolicitud(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudService.finalizarSolicitud(id));
    }

    @PutMapping("/{id}/asignar-sucursal")
    @Operation(summary = "Asignar sucursal a solicitud", description = "Asigna un empleado y una sucursal específica para atender la solicitud de soporte.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal asignada correctamente a la solicitud"),
            @ApiResponse(responseCode = "400", description = "Datos de asignación inválidos"),
            @ApiResponse(responseCode = "404", description = "Solicitud, empleado o sucursal no encontrados")
    })
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
    @Operation(summary = "Agregar evidencia", description = "Añade una nueva evidencia (documento, imagen, etc.) a una solicitud de soporte existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evidencia agregada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la evidencia inválidos"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada para asociar la evidencia")
    })
    public ResponseEntity<?> agregarEvidencia(
            @PathVariable Long id,
            @Valid @RequestBody Evidencia evidencia) {

        return ResponseEntity.ok(
                solicitudService.agregarEvidencia(
                        id,
                        evidencia));
    }

    @PutMapping("/{id}/devolucion")
    @Operation(summary = "Procesar devolución", description = "Inicia el proceso de devolución asociado a una solicitud de soporte.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devolución procesada correctamente"),
            @ApiResponse(responseCode = "400", description = "La solicitud no cumple con los requisitos para devolución"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<?> procesarDevolucion(
            @PathVariable Long id) {

        solicitudService.procesarDevolucion(id);

        return ResponseEntity.ok(
                "Devolución procesada correctamente");
    }

    @DeleteMapping("eliminar/{id}")
    @Operation(summary = "Eliminar solicitud", description = "Elimina una solicitud de soporte del sistema mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada para eliminar")
    })
    public ResponseEntity<?> eliminarSolicitud(
            @PathVariable Long id) {

        solicitudService.eliminarSolicitud(id);

        return ResponseEntity.ok(
                "Solicitud eliminada correctamente");
    }
}