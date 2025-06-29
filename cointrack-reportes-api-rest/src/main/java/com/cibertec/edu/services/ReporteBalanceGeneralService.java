package com.cibertec.edu.services;

import java.time.LocalDate;
import java.time.YearMonth;
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
    
    public List<ReporteBalanceGeneral> obtenerPorRango(Long usuarioId, int anioInicio, int mesInicio, int anioFin, int mesFin) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new BadRequestException("usuarioId inválido.");
        }

        if (mesInicio < 1 || mesInicio > 12 || mesFin < 1 || mesFin > 12) {
            throw new BadRequestException("Los meses deben estar entre 1 y 12.");
        }

        YearMonth inicio = YearMonth.of(anioInicio, mesInicio);
        YearMonth fin = YearMonth.of(anioFin, mesFin);

        if (inicio.isAfter(fin)) {
            throw new BadRequestException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        return repository.findByUsuarioIdAndRangoFecha(usuarioId, inicio.atDay(1), fin.atEndOfMonth());
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