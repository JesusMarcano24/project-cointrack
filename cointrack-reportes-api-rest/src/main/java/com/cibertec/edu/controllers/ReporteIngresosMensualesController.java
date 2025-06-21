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

import com.cibertec.edu.models.ReporteIngresosMensuales;
import com.cibertec.edu.services.ReporteIngresosMensualesService;

@RestController
@RequestMapping("/api/reportes/ingresos-mensuales")
public class ReporteIngresosMensualesController {

    @Autowired
    private ReporteIngresosMensualesService service;

    @PostMapping
    public ResponseEntity<ReporteIngresosMensuales> guardar(@RequestBody ReporteIngresosMensuales reporte) {
        return ResponseEntity.ok(service.guardar(reporte));
    }

    @GetMapping("/usuario/{usuarioId}/anio/{anio}/mes/{mes}")
    public ResponseEntity<List<ReporteIngresosMensuales>> obtenerPorUsuarioYMes(
            @PathVariable Long usuarioId,
            @PathVariable Integer anio,
            @PathVariable Integer mes) {
        return ResponseEntity.ok(service.obtenerPorUsuarioYMes(usuarioId, mes, anio));
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReporteIngresosMensuales>> obtenerTodosPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.obtenerTodosPorUsuario(usuarioId));
    }
}