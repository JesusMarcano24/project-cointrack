package com.cibertec.edu.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.edu.models.ReporteBalanceGeneral;

public interface IReporteBalanceGeneralRepository extends JpaRepository<ReporteBalanceGeneral, Long> {
    List<ReporteBalanceGeneral> findByUsuarioId(Long usuarioId);
    @Query("SELECT r FROM ReporteBalanceGeneral r WHERE r.usuarioId = :usuarioId AND r.fechaInicio >= :fechaDesde AND r.fechaFin <= :fechaHasta")
    List<ReporteBalanceGeneral> findByUsuarioIdAndRangoFecha(
        @Param("usuarioId") Long usuarioId,
        @Param("fechaDesde") LocalDate fechaDesde,
        @Param("fechaHasta") LocalDate fechaHasta
    );
}