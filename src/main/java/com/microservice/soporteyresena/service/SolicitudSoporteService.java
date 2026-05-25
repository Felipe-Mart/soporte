package com.microservice.soporteyresena.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.soporteyresena.model.EstadoSolicitud;
import com.microservice.soporteyresena.model.SolicitudSoporte;
import com.microservice.soporteyresena.repository.SolicitudSoporteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SolicitudSoporteService {
    @Autowired
    private SolicitudSoporteRepository solicitudSoporteRepository;

    public List<SolicitudSoporte> listar() {
        return solicitudSoporteRepository.findAll();
    }

    public SolicitudSoporte crearSolicitud(SolicitudSoporte solicitud) {

        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaSolicitud(LocalDate.now());

        return solicitudSoporteRepository.save(solicitud);
    }

    public EstadoSolicitud estadoSolicitud(Long id) {
        SolicitudSoporte solicitud =solicitudSoporteRepository.findById(id).orElse(null);

        if (solicitud != null) {
            return solicitud.getEstado();
        }
        return null;
    }

    public SolicitudSoporte buscarSolicitudId(Long id) {
        return solicitudSoporteRepository.findById(id).orElse(null);
    }

    public SolicitudSoporte actualizarSolicitud(Long id, SolicitudSoporte nuevaSolicitud) {

        SolicitudSoporte solicitud = solicitudSoporteRepository.findById(id).orElse(null);

        if (solicitud != null) {

            solicitud.setIdSolicitud(nuevaSolicitud.getIdSolicitud());
            solicitud.setTipoSolicitud(nuevaSolicitud.getTipoSolicitud());
            solicitud.setDescripcion(nuevaSolicitud.getDescripcion());
            solicitud.setEstado(nuevaSolicitud.getEstado());
            solicitud.setFechaSolicitud(nuevaSolicitud.getFechaSolicitud());
            solicitud.setIdCliente(nuevaSolicitud.getIdCliente());
            solicitud.setIdProducto(nuevaSolicitud.getIdProducto());

        return solicitudSoporteRepository.save(solicitud);
    }

    return null;
    }

    public void eliminarSolicitud(Long id) {
        solicitudSoporteRepository.deleteById(id);
    }
}
