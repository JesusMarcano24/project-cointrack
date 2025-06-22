package com.cibertec.repositories;


import com.cibertec.models.Transacciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransaccionesGrupalesRepository extends JpaRepository<Transacciones, Integer> {
    List<Transacciones> findByGrupoId(Integer grupoId);
    @Query("SELECT COALESCE(SUM(t.monto), 0) FROM Transacciones t WHERE t.grupo.id = :grupoId AND t.tipo = 'GASTO'")
    BigDecimal obtenerTotalGastos(@Param("grupoId") Integer grupoId);
    @Query("SELECT COALESCE(SUM(t.monto), 0) FROM Transacciones t WHERE t.grupo.id = :grupoId AND t.tipo = 'INGRESO'")
    BigDecimal obtenerTotalIngresos(@Param("grupoId") Integer grupoId);

}
