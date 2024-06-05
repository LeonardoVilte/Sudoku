package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorPerfil {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorPerfil(ServicioUsuario servicioUsuario)  {this.servicioUsuario = servicioUsuario;}

    @RequestMapping("/perfil")
    public ModelAndView mostrarPerfil(Model modelo, HttpServletRequest request) {
        // Obtener el email del usuario desde la sesión
        String email = (String) request.getSession().getAttribute("email"); // simula el usuario autenticado
        if (email == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = servicioUsuario.obtenerUsuarioPorEmail(email);

        if (usuario == null) {
            usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNombre("Usuario Ejemplo"); // O cualquier nombre por defecto
            servicioUsuario.actualizarUsuario(usuario);
        }

        modelo.addAttribute("nombre", usuario.getNombre());
        modelo.addAttribute("horasJugadas", usuario.getHorasJugadas());
        modelo.addAttribute("cantidadPartidasJugadas", usuario.getCantidadPartidasJugadas());
        modelo.addAttribute("tiempoPromedioResolucion", usuario.getTiempoPromedioResolucion());

        return new ModelAndView("perfil");
    }
}
