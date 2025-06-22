package com.cibertec.services;

import com.cibertec.models.Transacciones;
import com.cibertec.repositories.TransaccionesGrupalesRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionesGrupalesService {
    private final TransaccionesGrupalesRepository repository;

    public TransaccionesGrupalesService(TransaccionesGrupalesRepository repository) {
        this.repository = repository;
    }

    public List<Transacciones> listarPorGrupo(Integer grupoId) {
        return repository.findByGrupoId(grupoId);
    }

    public Transacciones guardar(Transacciones transaccion) {
        transaccion.setFechaCreacion(LocalDateTime.now());
        return repository.save(transaccion);
    }

    public Transacciones actualizar(Transacciones transaccion) {
        transaccion.setFechaActualizacion(LocalDateTime.now());
        return repository.save(transaccion);
    }

    public Optional<Transacciones> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    public BigDecimal obtenerTotalGastos(Integer grupoId) {
        return repository.obtenerTotalGastos(grupoId);
    }

    public BigDecimal obtenerTotalIngresos(Integer grupoId) {
        return repository.obtenerTotalIngresos(grupoId);
    }

}
