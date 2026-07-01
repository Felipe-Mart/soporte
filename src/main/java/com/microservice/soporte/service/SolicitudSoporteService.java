package com.microservice.soporte.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.microservice.soporte.dto.ClienteDTO;
import com.microservice.soporte.dto.EmpleadoDTO;
import com.microservice.soporte.dto.ProductoDTO;
import com.microservice.soporte.dto.SucursalDTO;
import com.microservice.soporte.model.EstadoSolicitud;
import com.microservice.soporte.model.Evidencia;
import com.microservice.soporte.model.SolicitudSoporte;
import com.microservice.soporte.model.TipoSolicitud;
import com.microservice.soporte.repository.SolicitudSoporteRepository;



@Service
@Transactional
public class SolicitudSoporteService {

    @Autowired
    private SolicitudSoporteRepository solicitudRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String URL_CLIENTES =
            "http://localhost:8081/api/clientes/buscar/";

    private static final String URL_EMPLEADOS =
            "http://localhost:8081/api/empleados/buscar/";

    private static final String URL_PRODUCTOS =
            "http://localhost:8086/api/productos/buscar/";

    private static final String URL_SUCURSALES =
            "http://localhost:8082/api/sucursales/buscar/";

    public SolicitudSoporte crearSolicitud(SolicitudSoporte solicitud) {

        // Verificar que existan el cliente y el producto
        ClienteDTO cliente = restTemplate.getForObject(
                URL_CLIENTES + solicitud.getIdCliente(),
                ClienteDTO.class);

        ProductoDTO producto = restTemplate.getForObject(
                URL_PRODUCTOS + solicitud.getIdProducto(),
                ProductoDTO.class);

        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }

        // Regla: solo los reclamos tienen categoría
        if (solicitud.getTipoSolicitud() == TipoSolicitud.RECLAMO
                && solicitud.getCategoriaReclamo() == null) {

            throw new RuntimeException(
                    "Los reclamos deben tener una categoría");
        }

        if (solicitud.getTipoSolicitud() != TipoSolicitud.RECLAMO
                && solicitud.getCategoriaReclamo() != null) {

            throw new RuntimeException(
                    "Solo los reclamos pueden tener categoría");
        }

        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaSolicitud(LocalDateTime.now());

        return solicitudRepository.save(solicitud);
    }

    public List<SolicitudSoporte> listarSolicitudes() {
        return solicitudRepository.findAll();
    }

    public SolicitudSoporte buscarSolicitud(Long id) {

        return solicitudRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Solicitud no encontrada"));
    }

    public SolicitudSoporte actualizarEstado(
            Long idSolicitud,
            EstadoSolicitud nuevoEstado) {

        SolicitudSoporte solicitud = buscarSolicitud(idSolicitud);

        solicitud.setEstado(nuevoEstado);

        return solicitudRepository.save(solicitud);
    }

    public SolicitudSoporte finalizarSolicitud(Long idSolicitud) {

        SolicitudSoporte solicitud = buscarSolicitud(idSolicitud);

        solicitud.setEstado(EstadoSolicitud.FINALIZADA);

        return solicitudRepository.save(solicitud);
    }

    public SolicitudSoporte asignarSucursal(
            Long idSolicitud,
            Long idEmpleado,
            Long idSucursal) {

        SolicitudSoporte solicitud = buscarSolicitud(idSolicitud);

        if (solicitud.getTipoSolicitud() != TipoSolicitud.RECLAMO) {
            throw new RuntimeException(
                    "Solo los reclamos pueden asignarse a una sucursal");
        }

        EmpleadoDTO empleado = restTemplate.getForObject(
                URL_EMPLEADOS + idEmpleado,
                EmpleadoDTO.class);

        SucursalDTO sucursal = restTemplate.getForObject(
                URL_SUCURSALES + idSucursal,
                SucursalDTO.class);

        if (empleado == null) {
            throw new RuntimeException("Empleado no encontrado");
        }

        if (sucursal == null) {
            throw new RuntimeException("Sucursal no encontrada");
        }

        solicitud.setIdEmpleado(idEmpleado);
        solicitud.setIdSucursal(idSucursal);

        System.out.println(
                "Empleado " + empleado.getNombre()
                + " asignó la sucursal "
                + sucursal.getNombre()
                + " ubicada en "
                + sucursal.getDireccion());

        return solicitudRepository.save(solicitud);
    }

    public SolicitudSoporte agregarEvidencia(
            Long idSolicitud,
            Evidencia evidencia) {

        SolicitudSoporte solicitud = buscarSolicitud(idSolicitud);

        if (solicitud.getTipoSolicitud() != TipoSolicitud.RECLAMO) {
            throw new RuntimeException(
                    "Solo los reclamos pueden tener evidencias");
        }

        evidencia.setSolicitudSoporte(solicitud);

        solicitud.getEvidencias().add(evidencia);

        return solicitudRepository.save(solicitud);
    }

    public void procesarDevolucion(Long idSolicitud) {

        SolicitudSoporte solicitud = buscarSolicitud(idSolicitud);

        if (solicitud.getTipoSolicitud() != TipoSolicitud.RECLAMO) {
            throw new RuntimeException(
                    "Solo los reclamos permiten devoluciones");
        }

        ClienteDTO cliente = restTemplate.getForObject(
                URL_CLIENTES + solicitud.getIdCliente(),
                ClienteDTO.class);

        ProductoDTO producto = restTemplate.getForObject(
                URL_PRODUCTOS + solicitud.getIdProducto(),
                ProductoDTO.class);

        System.out.println("""
                =====================================
                GESTIONANDO DEVOLUCIÓN
                =====================================
                Cliente:
                ID: %d
                Nombre: %s
                Email: %s

                Producto:
                ID: %d
                Nombre: %s
                Precio: %.2f
                =====================================
                """
                .formatted(
                        cliente.getIdCliente(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        producto.getIdProducto(),
                        producto.getNombProducto(),
                        producto.getPrecio()
                ));

        solicitud.setEstado(EstadoSolicitud.FINALIZADA);

        solicitudRepository.save(solicitud);
    }

    public void eliminarSolicitud(Long id) {

        if (!solicitudRepository.existsById(id)) {
            throw new RuntimeException("Solicitud no encontrada");
        }

        solicitudRepository.deleteById(id);
    }
}