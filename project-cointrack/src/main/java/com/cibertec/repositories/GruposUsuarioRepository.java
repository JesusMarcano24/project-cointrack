package com.cibertec.repositories;

import com.cibertec.models.GruposUsuario;
import com.cibertec.models.GruposUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GruposUsuarioRepository extends JpaRepository<GruposUsuario, GruposUsuarioId> {

    boolean existsById(GruposUsuarioId id);
}
