package com.cibertec.edu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.edu.models.ReporteMetasAhorro;

public interface IReporteMetasAhorroRepository extends JpaRepository<ReporteMetasAhorro, Long> {
    List<ReporteMetasAhorro> findByUsuarioId(Long usuarioId);
}