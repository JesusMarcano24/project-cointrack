package com.cibertec.controllers;

import com.cibertec.models.Grupos;
import com.cibertec.models.GruposUsuario;
import com.cibertec.models.Usuario;
import com.cibertec.services.GruposService;
import com.cibertec.services.GruposUsuarioService;
import com.cibertec.services.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/grupos")
public class GruposUsuarioController {

    private final GruposUsuarioService gruposUsuarioService;
    private final GruposService gruposService;
    private final UsuarioService usuarioService;

    public GruposUsuarioController(GruposUsuarioService gruposUsuarioService, GruposService gruposService, UsuarioService usuarioService) {
        this.gruposUsuarioService = gruposUsuarioService;
        this.gruposService = gruposService;
        this.usuarioService = usuarioService;
    }



    // Vista para mostrar participantes
    @GetMapping("/{grupoId}/participantes")
    public String verParticipantesGrupo(@PathVariable Integer grupoId, Model model) {
        Optional<Grupos> grupoOpt = gruposService.obtenerGrupoPorId(grupoId);
        if (grupoOpt.isPresent()) {
            Grupos grupo = grupoOpt.get();
            List<GruposUsuario> participantes = gruposUsuarioService.obtenerParticipantesPorGrupo(grupoId);
            BigDecimal totalIngresos = gruposUsuarioService.obtenerTotalIngresos(grupoId);

            model.addAttribute("grupo", grupo);
            model.addAttribute("participantes", participantes);
            model.addAttribute("totalIngresos", totalIngresos);
            return "fragments/participantes_grupos_fragment";
        } else {
            return "redirect:/grupos/lista";
        }
    }

    // Mostrar formulario para agregar participante
    @GetMapping("/{grupoId}/participantes/nuevo")
    public String mostrarFormularioAgregarParticipante(@PathVariable Integer grupoId, Model model) {
        Optional<Grupos> grupoOpt = gruposService.obtenerGrupoPorId(grupoId);
        if (grupoOpt.isPresent()) {
            model.addAttribute("grupo", grupoOpt.get());
            model.addAttribute("usuarios", usuarioService.listarUsuarios()); // para seleccionar de una lista
            return "transacciones_grupos/participantes_form";
        }
        return "redirect:/grupos/lista";
    }

    // Guardar nuevo participante
    @PostMapping("/{grupoId}/participantes/guardar")
    public String guardarParticipante(@PathVariable Integer grupoId,
                                      @RequestParam("usuarioId") Integer usuarioId,
                                      @RequestParam("saldoInicial") BigDecimal saldoInicial,
                                      Model model) {

        Optional<Grupos> grupoOpt = gruposService.obtenerGrupoPorId(grupoId);
        Optional<Usuario> usuarioOpt = usuarioService.findById(usuarioId);

        if (grupoOpt.isPresent() && usuarioOpt.isPresent()) {
            try {
                gruposUsuarioService.agregarParticipante(grupoOpt.get(), usuarioOpt.get(), saldoInicial);
                return "redirect:/grupos/ver/" + grupoId;
            } catch (Exception e) {
                model.addAttribute("grupo", grupoOpt.get());
                model.addAttribute("usuarios", usuarioService.listarUsuarios());
                model.addAttribute("error", e.getMessage());
                return "transacciones_grupos/participantes_form";
            }
        }

        return "redirect:/grupos/lista";
    }

    // Eliminar participante
    @GetMapping("/{grupoId}/participantes/eliminar/{usuarioId}")
    public String eliminarParticipante(@PathVariable Integer grupoId, @PathVariable Integer usuarioId) {
        gruposUsuarioService.eliminarParticipante(grupoId, usuarioId);
        return  "redirect:/grupos/ver/{grupoId}";
    }



    // === Helper para obtener usuario logueado ===
    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();

            return usuarioService.findByUsername(username).orElseThrow(
                    () -> new IllegalStateException("Usuario no encontrado para el username: " + username));
        }

        throw new IllegalStateException("No se encontró un usuario autenticado.");
    }
}