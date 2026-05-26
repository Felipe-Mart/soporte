package com.microservice.soporteyresena.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.soporteyresena.model.Calificacion;
import com.microservice.soporteyresena.model.ClienteDTO;
import com.microservice.soporteyresena.model.ProductoDTO;
import com.microservice.soporteyresena.repository.CalificacionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CalificacionService {
    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Calificacion> listarCalificaciones() {
        return calificacionRepository.findAll();
    }

    public Calificacion generarCalificacion(Calificacion calificacion) {

        String urlCliente ="http://localhost:8081/api/clientes/" + calificacion.getIdCliente();

        ClienteDTO cliente = restTemplate.getForObject( urlCliente, ClienteDTO.class);

        if (cliente == null) {
            throw new RuntimeException("El cliente no existe");
        }

        String urlProducto = "http://localhost:8082/api/productos/" + calificacion.getIdProducto();

        ProductoDTO producto = restTemplate.getForObject(urlProducto, ProductoDTO.class);

        if (producto == null) {
            throw new RuntimeException("El producto no existe");
        }

        calificacion.setFechaCalificacion(LocalDate.now());
        return calificacionRepository.save(calificacion);
    }

    public Calificacion buscarCalificacionId(Long id) {
        return calificacionRepository.findById(id).orElse(null);
    }

    public void eliminarCalificacion(Long id) {
        calificacionRepository.deleteById(id);
    }
}
