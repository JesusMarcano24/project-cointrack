package com.cibertec.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.models.Transacciones;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransaccionesRepository extends JpaRepository<Transacciones, Integer> {
	@Query("SELECT t FROM Transacciones t WHERE t.usuario.id = :usuarioId AND t.grupo IS NULL")
	List<Transacciones> findTransaccionesPersonales(@Param("usuarioId") Integer usuarioId);

	Optional<Transacciones> findByIdAndUsuarioId(Integer id, Integer usuarioId);
}
