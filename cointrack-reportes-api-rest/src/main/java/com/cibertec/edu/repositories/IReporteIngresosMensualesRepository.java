package com.cibertec.edu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.edu.models.ReporteIngresosMensuales;

public interface IReporteIngresosMensualesRepository extends JpaRepository<ReporteIngresosMensuales, Long> {
    List<ReporteIngresosMensuales> findByUsuarioIdAndMesAndAnio(Long usuarioId, Integer mes, Integer anio);
    
    List<ReporteIngresosMensuales> findByUsuarioIdAndAnio(Long usuarioId, Integer anio);
    
    List<ReporteIngresosMensuales> findByUsuarioId(Long usuarioId);
}