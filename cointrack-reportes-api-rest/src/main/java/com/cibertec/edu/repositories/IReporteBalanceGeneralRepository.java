package com.cibertec.edu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.edu.models.ReporteBalanceGeneral;

public interface IReporteBalanceGeneralRepository extends JpaRepository<ReporteBalanceGeneral, Long> {
    List<ReporteBalanceGeneral> findByUsuarioId(Long usuarioId);
}