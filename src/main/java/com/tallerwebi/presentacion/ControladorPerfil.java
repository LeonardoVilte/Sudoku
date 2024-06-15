package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorPerfil {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorPerfil(ServicioUsuario servicioUsuario)  {this.servicioUsuario = servicioUsuario;}

    @RequestMapping("/perfil")
    public String mostrarPerfil(Model modelo, HttpServletRequest request) {
        // Obtener el email del usuario desde la sesión
<<<<<<< Updated upstream
        String email = (String) request.getSession().getAttribute("mail");
=======
        String email = (String) request.getSession().getAttribute("email");
        String nombre = (String) request.getSession().getAttribute("Usuario");

        ModelMap model = new ModelMap();
        model.put("usuario", nombre);


>>>>>>> Stashed changes
        if (email == null) {
            return "redirect:/login";
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

<<<<<<< Updated upstream
        return "perfil";
=======
        return new ModelAndView("perfil", model);
>>>>>>> Stashed changes
    }
}
