package com.microservice.soporte.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.soporte.model.CategoriaReclamo;
import com.microservice.soporte.model.EstadoSolicitud;
import com.microservice.soporte.model.Evidencia;
import com.microservice.soporte.model.SolicitudSoporte;
import com.microservice.soporte.model.TipoSolicitud;
import com.microservice.soporte.repository.SolicitudSoporteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class SolicitudSoporteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SolicitudSoporteRepository repository;

    private SolicitudSoporte solicitud;

    @BeforeEach
    void setUp() {

        repository.deleteAll();

        solicitud = new SolicitudSoporte();

        solicitud.setIdCliente(1L);
        solicitud.setIdProducto(2L);

        // CAMBIAR POR TUS ENUMS REALES
        solicitud.setTipoSolicitud(
                TipoSolicitud.RECLAMO);

        solicitud.setCategoriaReclamo(
                CategoriaReclamo.PRODUCTO_DAÑADO);

        solicitud.setDescripcion(
                "Producto con fallas");

        solicitud.setEstado(
                EstadoSolicitud.PENDIENTE);

        solicitud.setFechaSolicitud(
                LocalDateTime.now());
    }

    @Test
    void crearSolicitudDebeRetornar201()
            throws Exception {

        mockMvc.perform(
                post("/api/v1/soporte")
                        .contentType(
                                org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper
                                        .writeValueAsString(
                                                solicitud)))
                .andExpect(
                        status().isCreated());
    }

    @Test
    void listarSolicitudesDebeRetornar200()
            throws Exception {

        repository.save(solicitud);

        mockMvc.perform(
                get("/api/v1/soporte"))
                .andExpect(
                        status().isOk());
    }

    @Test
    void buscarSolicitudDebeRetornar200()
            throws Exception {

        solicitud = repository.save(solicitud);

        mockMvc.perform(
                get("/api/v1/soporte/buscar/"
                        + solicitud.getIdSolicitud()))
                .andExpect(
                        status().isOk());
    }

    @Test
    void actualizarEstadoDebeRetornar200()
            throws Exception {

        solicitud = repository.save(solicitud);

        mockMvc.perform(
                put("/api/v1/soporte/"
                        + solicitud.getIdSolicitud()
                        + "/estado")
                        .param(
                                "estado",
                                EstadoSolicitud.EN_PROCESO.name()))
                .andExpect(
                        status().isOk());
    }

    @Test
    void finalizarSolicitudDebeRetornar200()
            throws Exception {

        solicitud = repository.save(solicitud);

        mockMvc.perform(
                put("/api/v1/soporte/"
                        + solicitud.getIdSolicitud()
                        + "/finalizar"))
                .andExpect(
                        status().isOk());
    }

    @Test
    void asignarSucursalDebeRetornar200()
            throws Exception {

        solicitud = repository.save(solicitud);

        mockMvc.perform(
                put("/api/v1/soporte/"
                        + solicitud.getIdSolicitud()
                        + "/asignar-sucursal")
                        .param(
                                "idEmpleado",
                                "10")
                        .param(
                                "idSucursal",
                                "20"))
                .andExpect(
                        status().isOk());
    }

    @Test
    void agregarEvidenciaDebeRetornar200()
            throws Exception {

        solicitud = repository.save(solicitud);

        Evidencia evidencia =
                new Evidencia();

        evidencia.setDescripcionArchivo(
                "Foto del producto defectuoso");

        evidencia.setTipoArchivo(
                "jpg");

        mockMvc.perform(
                post("/api/v1/soporte/"
                        + solicitud.getIdSolicitud()
                        + "/evidencias")
                        .contentType(
                                org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper
                                        .writeValueAsString(
                                                evidencia)))
                .andExpect(
                        status().isOk());
    }

    @Test
    void procesarDevolucionDebeRetornar200()
            throws Exception {

        solicitud = repository.save(solicitud);

        mockMvc.perform(
                put("/api/v1/soporte/"
                        + solicitud.getIdSolicitud()
                        + "/devolucion"))
                .andExpect(
                        status().isOk());
    }

    @Test
    void eliminarSolicitudDebeRetornar200()
            throws Exception {

        solicitud = repository.save(solicitud);

        mockMvc.perform(
                delete("/api/v1/soporte/eliminar/"
                        + solicitud.getIdSolicitud()))
                .andExpect(
                        status().isOk());
    }
}