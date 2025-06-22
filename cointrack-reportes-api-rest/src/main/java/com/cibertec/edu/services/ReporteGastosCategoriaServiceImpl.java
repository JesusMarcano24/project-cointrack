package com.cibertec.edu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.edu.exceptions.BadRequestException;
import com.cibertec.edu.models.ReporteGastosCategoria;
import com.cibertec.edu.repositories.IReporteGastosCategoriaRepository;

@Service
public class ReporteGastosCategoriaServiceImpl {

    @Autowired
    private IReporteGastosCategoriaRepository repository;

    public List<ReporteGastosCategoria> obtenerPorUsuario(Integer usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new BadRequestException("El usuarioId debe ser mayor que cero.");
        }
        return repository.findByUsuarioId(usuarioId);
    }
    
    public List<ReporteGastosCategoria> buscarPorUsuarioYCategoria(Integer usuarioId, String categoria) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new BadRequestException("El usuarioId debe ser mayor que cero.");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new BadRequestException("La categoría no puede estar vacía.");
        }
        return repository.findByUsuarioIdAndCategoriaContainingIgnoreCase(usuarioId, categoria.trim());
    }

    public ReporteGastosCategoria guardar(ReporteGastosCategoria reporte) {
        if (reporte == null || reporte.getUsuarioId() == null || 
            reporte.getCategoria() == null || reporte.getMontoTotal() == null ||
            reporte.getFechaInicio() == null || reporte.getFechaFin() == null) {
            throw new BadRequestException("Todos los campos del reporte son obligatorios.");
        }
        return repository.save(reporte);
    }
}