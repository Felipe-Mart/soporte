package com.microservice.soporteyresena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.soporteyresena.model.EstadoSolicitud;
import com.microservice.soporteyresena.model.Reclamo;
import com.microservice.soporteyresena.model.SolicitudSoporte;
import com.microservice.soporteyresena.model.TipoSolicitud;
import com.microservice.soporteyresena.repository.ReclamoRepository;
import com.microservice.soporteyresena.repository.SolicitudSoporteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReclamoService {
    @Autowired
    private ReclamoRepository reclamoRepository;
    private SolicitudSoporteRepository solicitudSoporteRepository;

    public List<Reclamo> listarReclamos() {
        return reclamoRepository.findAll();
    }

    public Reclamo crearReclamo(Reclamo reclamo) {

        if (reclamo.getSolicitudSoporte() == null) {
            throw new RuntimeException("El reclamo debe tener una solicitud de soporte.");
        }

        if (reclamo.getSolicitudSoporte().getTipoSolicitud() != TipoSolicitud.RECLAMO) {
            throw new RuntimeException("Solo las solicitudes tipo RECLAMO pueden generar reclamos.");
        }

        return reclamoRepository.save(reclamo);
    }

    public Reclamo buscarReclamoId(Long id) {
        return reclamoRepository.findById(id).orElse(null);
    }

    public Reclamo finalizarReclamo(Long idReclamo) {

        Reclamo reclamo =reclamoRepository.findById(idReclamo).orElse(null);

        if (reclamo == null) {
            throw new RuntimeException("El reclamo no existe");
        }
        
        SolicitudSoporte solicitud =reclamo.getSolicitudSoporte();

        if (solicitud == null) {
            throw new RuntimeException("El reclamo no tiene solicitud asociada");
        }

        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        solicitudSoporteRepository.save(solicitud);

        return reclamo;
    }


    public String consultarEstado(Long id) {

        Reclamo reclamo =reclamoRepository.findById(id).orElse(null);

        if (reclamo != null && reclamo.getSolicitudSoporte() != null) {
            return reclamo.getSolicitudSoporte().getEstado().toString();
        }

        return "Estado no disponible";
    }

    public Reclamo asignarSucursal(Long idReclamo,Long idSucursal) {

        Reclamo reclamo = reclamoRepository.findById(idReclamo).orElse(null);

        if (reclamo != null) {
            reclamo.setIdSucursal(idSucursal);
            return reclamoRepository.save(reclamo);
        }

        return null;
    }
}
