package com.cibertec.controllers;

import com.cibertec.models.Grupos;
import com.cibertec.models.GruposUsuario;
import com.cibertec.models.Transacciones;
import com.cibertec.models.Usuario;
import com.cibertec.services.GruposService;
import com.cibertec.services.GruposUsuarioService;
import com.cibertec.services.TransaccionesGrupalesService;
import com.cibertec.services.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/grupos")
public class GruposController {

    private final GruposService gruposService;
    private final UsuarioService usuarioService;
    private final GruposUsuarioService gruposUsuarioService;
    private final TransaccionesGrupalesService transaccionesGrupalesService;

    public GruposController(GruposService gruposService,
                            UsuarioService usuarioService,
                            GruposUsuarioService gruposUsuarioService,
                            TransaccionesGrupalesService transaccionesGrupalesService) {
        this.gruposService = gruposService;
        this.usuarioService = usuarioService;
        this.gruposUsuarioService = gruposUsuarioService;
        this.transaccionesGrupalesService = transaccionesGrupalesService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoGrupo(Model model) {
        model.addAttribute("grupo", new Grupos());
        return "transacciones_grupos/form";
    }


    @PostMapping("/guardar")
    public String guardarGrupo(@ModelAttribute("grupo") Grupos grupo, Model model) {
        try {
            Usuario usuario = obtenerUsuarioAutenticado();
            grupo.setUsuario(usuario);
            grupo.setFechaCreacion(LocalDate.now());
            gruposService.crearGrupo(grupo);
            return "redirect:/grupos/lista";
        } catch (Exception e) {
            model.addAttribute("grupo", grupo);
            model.addAttribute("error", "No se pudo guardar el grupo.");
            return "transacciones/grupos_form";
        }
    }

    @GetMapping("/lista")
    public String listarGrupos(Model model) {
        Usuario usuario = obtenerUsuarioAutenticado();
        List<Grupos> grupos = gruposService.listarGruposPorUsuario(usuario.getId());
        model.addAttribute("grupos", grupos);
        model.addAttribute("usuarioId", usuario.getId());
        return "transacciones_grupos/list_grupos";
    }

    @GetMapping("/ver/{grupoId}")
    public String verGrupo(@PathVariable Integer grupoId, Model model) {
        Optional<Grupos> grupoOpt = gruposService.obtenerGrupoPorId(grupoId);

        if (grupoOpt.isPresent()) {
            Grupos grupo = grupoOpt.get();

            List<GruposUsuario> participantes = gruposUsuarioService.obtenerParticipantesPorGrupo(grupoId);
            BigDecimal ingresosIniciales = gruposUsuarioService.obtenerTotalIngresos(grupoId);
            BigDecimal ingresosAdicionales = transaccionesGrupalesService.obtenerTotalIngresos(grupoId);
            BigDecimal totalGastos = transaccionesGrupalesService.obtenerTotalGastos(grupoId);
            List<Transacciones> transacciones = transaccionesGrupalesService.listarPorGrupo(grupoId);

            model.addAttribute("grupo", grupo);
            model.addAttribute("participantes", participantes);
            model.addAttribute("totalIngresos", ingresosIniciales);
            model.addAttribute("totalGastos", totalGastos);
            model.addAttribute("ingresosAdicionales", ingresosAdicionales);
            model.addAttribute("transacciones", transacciones);
            model.addAttribute("grupoId", grupoId);

            return "transacciones_grupos/index";
        } else {
            model.addAttribute("error", "Grupo no encontrado");
            return "redirect:/grupos/lista";
        }
    }

    @GetMapping("/eliminar/{grupoId}")
    public String eliminarGrupo(@PathVariable Integer grupoId, Model model) {
        try {
            Usuario usuario = obtenerUsuarioAutenticado();
            gruposService.eliminarGrupo(grupoId);
            return "redirect:/grupos/lista";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "transacciones/list_grupos";
        }
    }

    //Metodo para obtener el usuario logueado, igual que en TransaccionesController
    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();

            return usuarioService.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException("Usuario no encontrado: " + username));
        }

        throw new IllegalStateException("No se encontró un usuario autenticado.");
    }
}