package com.cibertec.edu.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.edu.models.ReporteMetasAhorro;
import com.cibertec.edu.services.ReporteMetasAhorroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reportes/metas-ahorro")
@RequiredArgsConstructor
public class ReporteMetasAhorroController {
	
	@Autowired
    private ReporteMetasAhorroService service;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReporteMetasAhorro>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.obtenerPorUsuario(usuarioId));
    }
    
    @GetMapping("/usuario/{usuarioId}/buscar-meta/{nombre}")
    public ResponseEntity<List<ReporteMetasAhorro>> buscarPorNombreYUsuario(
            @PathVariable Long usuarioId,
            @PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarPorNombreYUsuario(usuarioId, nombre));
    }

    @PostMapping
    public ResponseEntity<ReporteMetasAhorro> guardar(@RequestBody ReporteMetasAhorro reporte) {
        return ResponseEntity.ok(service.guardar(reporte));
    }
}