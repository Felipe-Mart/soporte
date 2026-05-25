package com.microservice.soporteyresena.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.soporteyresena.model.DevolucionProducto;
import com.microservice.soporteyresena.model.EstadoDevolucion;
import com.microservice.soporteyresena.model.Reclamo;
import com.microservice.soporteyresena.repository.DevolucionProductoRepository;
import com.microservice.soporteyresena.repository.ReclamoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DevolucionProductoService {
    @Autowired
    private DevolucionProductoRepository devolucionProductoRepository;
    private ReclamoRepository reclamoRepository;


    public DevolucionProducto crearDevolucion(Long idReclamo,DevolucionProducto devolucion) {

        Reclamo reclamo = reclamoRepository.findById(idReclamo).orElse(null);

        if (reclamo == null) {
            throw new RuntimeException("El reclamo no existe");
        }

        devolucion.setFechaDevolucion(LocalDate.now());
        devolucion.setEstado(EstadoDevolucion.PENDIENTE);
        devolucion.setReclamo(reclamo);

        return devolucionProductoRepository.save(devolucion);
    }

    public DevolucionProducto aprobarDevolucion(Long idDevolucion) {

        DevolucionProducto devolucion =devolucionProductoRepository.findById(idDevolucion).orElse(null);

        if (devolucion == null) {
            throw new RuntimeException("La devolución no existe");
        }

        devolucion.setEstado(EstadoDevolucion.APROBADA);

        return devolucionProductoRepository.save(devolucion);
    }

    public DevolucionProducto rechazarDevolucion(Long idDevolucion) {

        DevolucionProducto devolucion =devolucionProductoRepository.findById(idDevolucion).orElse(null);

        if (devolucion == null) {
            throw new RuntimeException("La devolución no existe");
        }

        devolucion.setEstado(EstadoDevolucion.RECHAZADA);

        return devolucionProductoRepository.save(devolucion);
    }

    public EstadoDevolucion consultarEstado(Long idDevolucion) {

        DevolucionProducto devolucion =devolucionProductoRepository.findById(idDevolucion).orElse(null);

        if (devolucion == null) {
            throw new RuntimeException("La devolución no existe");
        }

        return devolucion.getEstado();
    }

}
