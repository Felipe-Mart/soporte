package com.microservice.soporte.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.soporte.model.EstadoSolicitud;
import com.microservice.soporte.model.Evidencia;
import com.microservice.soporte.model.SolicitudSoporte;
import com.microservice.soporte.service.SolicitudSoporteService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SolicitudSoporteController.class)
@ActiveProfiles("test")
class SolicitudSoporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SolicitudSoporteService solicitudService;

    private ObjectMapper objectMapper =
            new ObjectMapper();

    @Test
    void testCrearSolicitud() throws Exception {

        SolicitudSoporte solicitud =
                new SolicitudSoporte();

        solicitud.setIdSolicitud(1L);

        when(solicitudService.crearSolicitud(any()))
                .thenReturn(solicitud);

        mockMvc.perform(post("/api/v1/soporte")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitud)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSolicitud")
                        .value(1L));
    }

    @Test
    void testListarSolicitudes() throws Exception {

        SolicitudSoporte s1 =
                new SolicitudSoporte();

        s1.setIdSolicitud(1L);

        SolicitudSoporte s2 =
                new SolicitudSoporte();

        s2.setIdSolicitud(2L);

        when(solicitudService.listarSolicitudes())
                .thenReturn(Arrays.asList(s1, s2));

        mockMvc.perform(get("/api/v1/soporte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(2));
    }

    @Test
    void testBuscarSolicitud() throws Exception {

        SolicitudSoporte solicitud =
                new SolicitudSoporte();

        solicitud.setIdSolicitud(1L);

        when(solicitudService.buscarSolicitud(1L))
                .thenReturn(solicitud);

        mockMvc.perform(get("/api/v1/soporte/buscar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSolicitud")
                        .value(1L));
    }

    @Test
    void testActualizarEstado() throws Exception {

        SolicitudSoporte solicitud =
                new SolicitudSoporte();

        solicitud.setEstado(
                EstadoSolicitud.EN_PROCESO);

        when(solicitudService.actualizarEstado(
                1L,
                EstadoSolicitud.EN_PROCESO))
                .thenReturn(solicitud);

        mockMvc.perform(
                put("/api/v1/soporte/1/estado")
                        .param("estado", "EN_PROCESO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado")
                        .value("EN_PROCESO"));
    }

    @Test
    void testFinalizarSolicitud() throws Exception {

        SolicitudSoporte solicitud =
                new SolicitudSoporte();

        solicitud.setEstado(
                EstadoSolicitud.FINALIZADA);

        when(solicitudService.finalizarSolicitud(1L))
                .thenReturn(solicitud);

        mockMvc.perform(
                put("/api/v1/soporte/1/finalizar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado")
                        .value("FINALIZADA"));
    }

    @Test
    void testAsignarSucursal() throws Exception {

        SolicitudSoporte solicitud =
                new SolicitudSoporte();

        solicitud.setIdEmpleado(10L);
        solicitud.setIdSucursal(20L);

        when(solicitudService.asignarSucursal(
                1L,
                10L,
                20L))
                .thenReturn(solicitud);

        mockMvc.perform(
                put("/api/v1/soporte/1/asignar-sucursal")
                        .param("idEmpleado", "10")
                        .param("idSucursal", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEmpleado")
                        .value(10))
                .andExpect(jsonPath("$.idSucursal")
                        .value(20));
    }

    @Test
    void testAgregarEvidencia() throws Exception {

        Evidencia evidencia =
                new Evidencia();

        evidencia.setDescripcionArchivo(
                "Foto del producto");

        SolicitudSoporte solicitud =
                new SolicitudSoporte();

        when(solicitudService.agregarEvidencia(
                eq(1L),
                any(Evidencia.class)))
                .thenReturn(solicitud);

        mockMvc.perform(
                post("/api/v1/soporte/1/evidencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evidencia)))
                .andExpect(status().isOk());
    }

    @Test
    void testProcesarDevolucion() throws Exception {

        doNothing()
                .when(solicitudService)
                .procesarDevolucion(1L);

        mockMvc.perform(
                put("/api/v1/soporte/1/devolucion"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Devolución procesada correctamente"));
    }

    @Test
    void testEliminarSolicitud() throws Exception {

        doNothing()
                .when(solicitudService)
                .eliminarSolicitud(1L);

        mockMvc.perform(
                delete("/api/v1/soporte/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Solicitud eliminada correctamente"));
    }
}