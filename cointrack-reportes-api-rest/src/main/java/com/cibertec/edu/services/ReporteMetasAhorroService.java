package com.cibertec.edu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.edu.exceptions.BadRequestException;
import com.cibertec.edu.models.ReporteMetasAhorro;
import com.cibertec.edu.repositories.IReporteMetasAhorroRepository;

@Service
public class ReporteMetasAhorroService {
    @Autowired
    private IReporteMetasAhorroRepository repository;

    public List<ReporteMetasAhorro> obtenerPorUsuario(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new BadRequestException("El usuarioId debe ser válido.");
        }
        return repository.findByUsuarioId(usuarioId);
    }

    public ReporteMetasAhorro guardar(ReporteMetasAhorro reporte) {
        if (reporte == null || reporte.getUsuarioId() == null ||
            reporte.getMetaNombre() == null || reporte.getMontoObjetivo() == null ||
            reporte.getMontoActual() == null || reporte.getPorcentajeAvance() == null) {
            throw new BadRequestException("Todos los campos del reporte son obligatorios.");
        }
        return repository.save(reporte);
    }
}