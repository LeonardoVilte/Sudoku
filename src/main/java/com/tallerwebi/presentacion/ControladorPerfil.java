package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalTime;

@Controller
public class ControladorPerfil {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorPerfil(ServicioUsuario servicioUsuario)  {this.servicioUsuario = servicioUsuario;}

    @RequestMapping("/perfil/{usuario}")
    public ModelAndView mostrarPerfil(Model modelo, HttpServletRequest request, @PathVariable ("usuario") String nombreUsuario){

        if(!this.servicioUsuario.siUsuarioExiste(nombreUsuario)){
            return new ModelAndView("redirect:/login");
        }
        Usuario usuario = this.servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario);
        LocalTime tiempoJugado = this.servicioUsuario.obtenerTiempoJugadoEnTodasLasPartidas(usuario);
        Integer partidasCompletadas = this.servicioUsuario.obtenerCantidadDePartidasCompletadas(usuario);
        LocalTime promedioResolucion = this.servicioUsuario.obtenerTiempoPromedioDeResolucion(usuario);

        modelo.addAttribute("nombre", usuario.getNombre());
        modelo.addAttribute("horasJugadas", tiempoJugado);
        modelo.addAttribute("cantidadPartidasJugadas", usuario.getCantidadPartidasJugadas());
        modelo.addAttribute("cantidasDePartidasCompletadas", partidasCompletadas);
        modelo.addAttribute("tiempoPromedioResolucion", promedioResolucion);
        modelo.addAttribute("monedas", usuario.getMonedas());

        return new ModelAndView("perfil");
    }
}
