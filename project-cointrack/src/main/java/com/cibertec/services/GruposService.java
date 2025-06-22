package com.cibertec.services;

import com.cibertec.models.Grupos;
import com.cibertec.repositories.GruposRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GruposService {
    private final GruposRepository gruposRepository;

    public GruposService(GruposRepository gruposRepository) {
        this.gruposRepository = gruposRepository;
    }

    public Grupos crearGrupo(Grupos grupo) {
        return gruposRepository.save(grupo);
    }

    public Optional<Grupos> obtenerGrupoPorId(Integer grupoId) {
        return gruposRepository.findById(grupoId);
    }

    public List<Grupos> listarGruposPorUsuario(Integer usuarioId) {
        return gruposRepository.findByUsuarioId(usuarioId);
    }

    public void eliminarGrupo(Integer grupoId) {
        gruposRepository.deleteById(grupoId);
    }
}
