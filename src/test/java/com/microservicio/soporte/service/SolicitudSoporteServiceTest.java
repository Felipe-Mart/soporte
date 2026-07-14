package com.microservicio.soporte.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.microservicio.soporte.dto.ClienteDTO;
import com.microservicio.soporte.dto.EmpleadoDTO;
import com.microservicio.soporte.dto.ProductoDTO;
import com.microservicio.soporte.dto.SucursalDTO;
import com.microservicio.soporte.model.CategoriaReclamo;
import com.microservicio.soporte.model.EstadoSolicitud;
import com.microservicio.soporte.model.Evidencia;
import com.microservicio.soporte.model.SolicitudSoporte;
import com.microservicio.soporte.model.TipoSolicitud;
import com.microservicio.soporte.repository.SolicitudSoporteRepository;

@ExtendWith(MockitoExtension.class)
class SolicitudSoporteServiceTest {

    @Mock
    private SolicitudSoporteRepository solicitudRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SolicitudSoporteService solicitudService;

    private SolicitudSoporte solicitud;

    @BeforeEach
    void setUp() {

        solicitud = new SolicitudSoporte();

        solicitud.setIdSolicitud(1L);
        solicitud.setIdCliente(1L);
        solicitud.setIdProducto(1L);
        solicitud.setTipoSolicitud(TipoSolicitud.RECLAMO);
        solicitud.setCategoriaReclamo(CategoriaReclamo.PRODUCTO_DAÑADO);
        solicitud.setEvidencias(new ArrayList<>());

    }

    @Test
    void testCrearSolicitud() {

        ClienteDTO cliente = new ClienteDTO();
        ProductoDTO producto = new ProductoDTO();

        when(restTemplate.getForObject(contains("clientes"),
                eq(ClienteDTO.class)))
                .thenReturn(cliente);

        when(restTemplate.getForObject(contains("productos"),
                eq(ProductoDTO.class)))
                .thenReturn(producto);

        when(solicitudRepository.save(any()))
                .thenReturn(solicitud);

        SolicitudSoporte resultado = solicitudService.crearSolicitud(solicitud);

        assertNotNull(resultado);
        assertEquals(EstadoSolicitud.PENDIENTE,
                resultado.getEstado());

        verify(solicitudRepository).save(solicitud);

    }

    @Test
    void testCrearSolicitudClienteNoExiste() {

        when(restTemplate.getForObject(
                contains("clientes"),
                eq(ClienteDTO.class)))
                .thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.crearSolicitud(solicitud));

        assertEquals("Cliente no encontrado",
                ex.getMessage());

    }

    @Test
    void testCrearSolicitudProductoNoExiste() {

        ClienteDTO cliente = new ClienteDTO();

        when(restTemplate.getForObject(
                contains("clientes"),
                eq(ClienteDTO.class)))
                .thenReturn(cliente);

        when(restTemplate.getForObject(
                contains("productos"),
                eq(ProductoDTO.class)))
                .thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.crearSolicitud(solicitud));

        assertEquals("Producto no encontrado",
                ex.getMessage());

    }

    @Test
    void testCrearSolicitudReclamoSinCategoria() {

        solicitud.setCategoriaReclamo(null);

        ClienteDTO cliente = new ClienteDTO();
        ProductoDTO producto = new ProductoDTO();

        when(restTemplate.getForObject(anyString(),
                eq(ClienteDTO.class)))
                .thenReturn(cliente);

        when(restTemplate.getForObject(anyString(),
                eq(ProductoDTO.class)))
                .thenReturn(producto);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.crearSolicitud(solicitud));

        assertEquals("Los reclamos deben tener una categoría",
                ex.getMessage());

    }

    @Test
    void testCrearConsultaConCategoria() {

        solicitud.setTipoSolicitud(TipoSolicitud.CONSULTA);

        ClienteDTO cliente = new ClienteDTO();
        ProductoDTO producto = new ProductoDTO();

        when(restTemplate.getForObject(anyString(),
                eq(ClienteDTO.class)))
                .thenReturn(cliente);

        when(restTemplate.getForObject(anyString(),
                eq(ProductoDTO.class)))
                .thenReturn(producto);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.crearSolicitud(solicitud));

        assertEquals("Solo los reclamos pueden tener categoría",
                ex.getMessage());

    }

    @Test
    void testListarSolicitudes() {

        List<SolicitudSoporte> lista = List.of(solicitud);

        when(solicitudRepository.findAll())
                .thenReturn(lista);

        List<SolicitudSoporte> resultado = solicitudService.listarSolicitudes();

        assertEquals(1, resultado.size());

        verify(solicitudRepository).findAll();
    }

    @Test
    void testBuscarSolicitud() {

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        SolicitudSoporte resultado = solicitudService.buscarSolicitud(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdSolicitud());
    }

    @Test
    void testBuscarSolicitudNoExiste() {

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.buscarSolicitud(1L));

        assertEquals("Solicitud no encontrada",
                ex.getMessage());
    }

    @Test
    void testActualizarEstado() {

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        when(solicitudRepository.save(any()))
                .thenReturn(solicitud);

        SolicitudSoporte resultado = solicitudService.actualizarEstado(
                1L,
                EstadoSolicitud.EN_PROCESO);

        assertEquals(
                EstadoSolicitud.EN_PROCESO,
                resultado.getEstado());

        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void testFinalizarSolicitud() {

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        when(solicitudRepository.save(any()))
                .thenReturn(solicitud);

        SolicitudSoporte resultado = solicitudService.finalizarSolicitud(1L);

        assertEquals(
                EstadoSolicitud.FINALIZADA,
                resultado.getEstado());

        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void testAsignarSucursal() {

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setNombre("Pedro");

        SucursalDTO sucursal = new SucursalDTO();
        sucursal.setCiudad("Sucursal Centro");

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        when(restTemplate.getForObject(
                contains("empleados"),
                eq(EmpleadoDTO.class)))
                .thenReturn(empleado);

        when(restTemplate.getForObject(
                contains("sucursales"),
                eq(SucursalDTO.class)))
                .thenReturn(sucursal);

        when(solicitudRepository.save(any()))
                .thenReturn(solicitud);

        SolicitudSoporte resultado = solicitudService.asignarSucursal(1L, 2L, 3L);

        assertEquals(2L, resultado.getIdEmpleado());
        assertEquals(3L, resultado.getIdSucursal());

        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void testAsignarSucursalConsulta() {

        solicitud.setTipoSolicitud(TipoSolicitud.CONSULTA);

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.asignarSucursal(1L, 2L, 3L));

        assertEquals(
                "Solo los reclamos pueden asignarse a una sucursal",
                ex.getMessage());
    }

    @Test
    void testAsignarSucursalEmpleadoNoExiste() {

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        when(restTemplate.getForObject(
                contains("empleados"),
                eq(EmpleadoDTO.class)))
                .thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.asignarSucursal(1L, 2L, 3L));

        assertEquals("Empleado no encontrado",
                ex.getMessage());
    }

    @Test
    void testAsignarSucursalNoExiste() {

        EmpleadoDTO empleado = new EmpleadoDTO();

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        when(restTemplate.getForObject(
                contains("empleados"),
                eq(EmpleadoDTO.class)))
                .thenReturn(empleado);

        when(restTemplate.getForObject(
                contains("sucursales"),
                eq(SucursalDTO.class)))
                .thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.asignarSucursal(1L, 2L, 3L));

        assertEquals("Sucursal no encontrada",
                ex.getMessage());
    }

    @Test
    void testAgregarEvidencia() {

        Evidencia evidencia = new Evidencia();

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        when(solicitudRepository.save(any()))
                .thenReturn(solicitud);

        SolicitudSoporte resultado = solicitudService.agregarEvidencia(1L, evidencia);

        assertTrue(resultado.getEvidencias().contains(evidencia));

        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void testAgregarEvidenciaConsulta() {

        solicitud.setTipoSolicitud(TipoSolicitud.CONSULTA);

        Evidencia evidencia = new Evidencia();

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.agregarEvidencia(1L, evidencia));

        assertEquals(
                "Solo los reclamos pueden tener evidencias",
                ex.getMessage());
    }

    @Test
    void testProcesarDevolucion() {

        ClienteDTO cliente = new ClienteDTO();
        cliente.setIdCliente(1L);
        cliente.setNombre("Vicente");
        cliente.setEmail("vicente@email.com");

        ProductoDTO producto = new ProductoDTO();
        producto.setIdProducto(1L);
        producto.setNombProducto("Notebook");
        producto.setPrecio(1000.0);

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        when(restTemplate.getForObject(
                contains("clientes"),
                eq(ClienteDTO.class)))
                .thenReturn(cliente);

        when(restTemplate.getForObject(
                contains("productos"),
                eq(ProductoDTO.class)))
                .thenReturn(producto);

        solicitudService.procesarDevolucion(1L);

        assertEquals(
                EstadoSolicitud.FINALIZADA,
                solicitud.getEstado());

        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void testProcesarDevolucionConsulta() {

        solicitud.setTipoSolicitud(TipoSolicitud.CONSULTA);

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.procesarDevolucion(1L));

        assertEquals(
                "Solo los reclamos permiten devoluciones",
                ex.getMessage());
    }

    @Test
    void testEliminarSolicitud() {

        when(solicitudRepository.existsById(1L))
                .thenReturn(true);

        solicitudService.eliminarSolicitud(1L);

        verify(solicitudRepository).deleteById(1L);
    }

    @Test
    void testEliminarSolicitudNoExiste() {

        when(solicitudRepository.existsById(1L))
                .thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> solicitudService.eliminarSolicitud(1L));

        assertEquals(
                "Solicitud no encontrada",
                ex.getMessage());
    }

}
