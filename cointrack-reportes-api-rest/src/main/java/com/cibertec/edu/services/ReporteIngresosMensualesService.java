package com.cibertec.edu.services;

import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.edu.exceptions.BadRequestException;
import com.cibertec.edu.models.ReporteIngresosMensuales;
import com.cibertec.edu.repositories.IReporteIngresosMensualesRepository;

@Service
public class ReporteIngresosMensualesService {

    @Autowired
    private IReporteIngresosMensualesRepository repository;
    
    public List<ReporteIngresosMensuales> obtenerTodosPorUsuario(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new BadRequestException("El usuarioId debe ser válido.");
        }
        return repository.findByUsuarioId(usuarioId);
    }

    public List<ReporteIngresosMensuales> obtenerPorUsuarioYMes(Long usuarioId, Integer mes, Integer anio) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new BadRequestException("El usuarioId debe ser válido.");
        }
        if (mes == null || mes < 1 || mes > 12) {
            throw new BadRequestException("El mes debe estar entre 1 y 12.");
        }
        if (anio == null || anio < 1900) {
            throw new BadRequestException("El año es inválido.");
        }

        return repository.findByUsuarioIdAndMesAndAnio(usuarioId, mes, anio);
    }
    
    public List<ReporteIngresosMensuales> obtenerPorUsuarioYAnio(Long usuarioId, Integer anio) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new BadRequestException("El usuarioId debe ser válido.");
        }
        if (anio == null || anio < 1900 || anio > Year.now().getValue()) {
            throw new BadRequestException("El año ingresado no es válido.");
        }
        return repository.findByUsuarioIdAndAnio(usuarioId, anio);
    }

    public ReporteIngresosMensuales guardar(ReporteIngresosMensuales reporte) {
        if (reporte == null || reporte.getUsuarioId() == null || reporte.getMes() == null ||
            reporte.getAnio() == null || reporte.getTotalIngresos() == null || reporte.getFechaReporte() == null) {
            throw new BadRequestException("Todos los campos son obligatorios y no pueden ser nulos.");
        }
        return repository.save(reporte);
    }
}