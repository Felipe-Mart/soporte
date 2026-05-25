package com.microservice.soporteyresena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.soporteyresena.model.Evidencia;
import com.microservice.soporteyresena.service.EvidenciaService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/evidencias")
public class EvidenciaController {
    @Autowired
    private EvidenciaService evidenciaService;

    @PostMapping("/reclamo/{idReclamo}")
    public Evidencia adjuntarEvidencia(@PathVariable Long idReclamo, @RequestBody Evidencia evidencia) {

        return evidenciaService.agregarEvidencia(idReclamo,evidencia);
    }
    

}
