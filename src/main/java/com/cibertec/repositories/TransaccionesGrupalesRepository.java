package com.cibertec.repositories;


import com.cibertec.models.Transacciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionesGrupalesRepository extends JpaRepository<Transacciones, Integer> {
    List<Transacciones> findByGrupoId(Integer grupoId);
}
