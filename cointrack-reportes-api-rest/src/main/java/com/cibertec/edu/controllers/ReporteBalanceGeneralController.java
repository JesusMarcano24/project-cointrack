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

import com.cibertec.edu.models.ReporteBalanceGeneral;
import com.cibertec.edu.services.ReporteBalanceGeneralService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reportes/balance-general")
@RequiredArgsConstructor
public class ReporteBalanceGeneralController {
	
	@Autowired
    private ReporteBalanceGeneralService service;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReporteBalanceGeneral>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.obtenerPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<ReporteBalanceGeneral> guardar(@RequestBody ReporteBalanceGeneral reporte) {
        return ResponseEntity.ok(service.guardar(reporte));
    }
}