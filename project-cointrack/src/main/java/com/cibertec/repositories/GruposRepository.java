package com.cibertec.repositories;

import com.cibertec.models.Grupos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GruposRepository extends JpaRepository<Grupos, Integer> {
    List<Grupos> findByUsuarioId(Integer usuarioId);
}
