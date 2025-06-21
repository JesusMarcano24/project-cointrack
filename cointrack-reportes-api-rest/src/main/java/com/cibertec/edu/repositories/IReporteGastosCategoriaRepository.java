package com.cibertec.edu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.edu.models.ReporteGastosCategoria;

@Repository
public interface IReporteGastosCategoriaRepository extends JpaRepository<ReporteGastosCategoria, Integer> {
	List<ReporteGastosCategoria> findByUsuarioId(Integer usuarioId);
}