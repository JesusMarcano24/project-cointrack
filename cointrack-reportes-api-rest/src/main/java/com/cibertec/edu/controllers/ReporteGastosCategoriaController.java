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

import com.cibertec.edu.models.ReporteGastosCategoria;
import com.cibertec.edu.services.ReporteGastosCategoriaServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reportes/gastos-categoria")
@RequiredArgsConstructor
public class ReporteGastosCategoriaController {
	
	@Autowired
    private ReporteGastosCategoriaServiceImpl service;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReporteGastosCategoria>> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerPorUsuario(usuarioId));
    }
    
    @GetMapping("/usuario/{usuarioId}/buscar-categoria/{categoria}")
    public ResponseEntity<List<ReporteGastosCategoria>> buscarPorUsuarioYCategoria(
            @PathVariable Integer usuarioId,
            @PathVariable String categoria) {
        return ResponseEntity.ok(service.buscarPorUsuarioYCategoria(usuarioId, categoria));
    }

    @PostMapping
    public ResponseEntity<ReporteGastosCategoria> guardar(@RequestBody ReporteGastosCategoria reporte) {
        return ResponseEntity.ok(service.guardar(reporte));
    }
}