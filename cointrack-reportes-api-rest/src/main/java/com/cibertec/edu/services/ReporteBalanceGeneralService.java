package com.cibertec.edu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.edu.exceptions.BadRequestException;
import com.cibertec.edu.models.ReporteBalanceGeneral;
import com.cibertec.edu.repositories.IReporteBalanceGeneralRepository;

@Service
public class ReporteBalanceGeneralService {
    @Autowired
    private IReporteBalanceGeneralRepository repository;

    public List<ReporteBalanceGeneral> obtenerPorUsuario(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new BadRequestException("El usuarioId debe ser válido.");
        }
        return repository.findByUsuarioId(usuarioId);
    }

    public ReporteBalanceGeneral guardar(ReporteBalanceGeneral reporte) {
        if (reporte == null || reporte.getUsuarioId() == null ||
            reporte.getTotalGastos() == null || reporte.getTotalIngresos() == null ||
            reporte.getBalance() == null || reporte.getFechaInicio() == null ||
            reporte.getFechaFin() == null) {
            throw new BadRequestException("Todos los campos del reporte son obligatorios.");
        }
        return repository.save(reporte);
    }

}