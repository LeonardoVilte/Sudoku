package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.infraestructura.ServicioUsuarioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorHome {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorHome(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null) {
            String email = (String) session.getAttribute("email");
            Usuario usuarioBuscado = this.servicioUsuario.obtenerUsuarioPorEmail(email);
            ModelMap model = new ModelMap();
            model.put("monedas", usuarioBuscado.getMonedas());
            return new ModelAndView("home", model);
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return new ModelAndView("redirect:/login?logout");
    }


    @RequestMapping("/dificultad")
    public ModelAndView dificultad() {
        return new ModelAndView("dificultad");
    }

    @RequestMapping("/mercado")
    public ModelAndView mercado() {
        return new ModelAndView("mercado");
    }

    @RequestMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

}