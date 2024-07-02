package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioJuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorHome {

    private ServicioJuego servicioJuego;
    @Autowired
    public ControladorHome(ServicioJuego servicioJuego){
        this.servicioJuego = servicioJuego;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            String nombreUsuario = (String) session.getAttribute("Usuario");
            ModelMap model = new ModelMap();
            model.put("nombreUsuario" , nombreUsuario);
            return new ModelAndView("home", model);
        }else {
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
    public ModelAndView dificultad(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null){

        String nombreUsuario = (String) session.getAttribute("Usuario");

        List<Partida> listaPartidasFaciles = this.servicioJuego.traer3MejoresTiemposPorDificultad(1);
        List<Partida> listaPartidasNormales = this.servicioJuego.traer3MejoresTiemposPorDificultad(2);
        List<Partida> listaPartidasDificiles = this.servicioJuego.traer3MejoresTiemposPorDificultad(3);

        ModelMap modelMap = new ModelMap();
        modelMap.put("listaFaciles" , listaPartidasFaciles);
        modelMap.put("listaNormales", listaPartidasNormales);
        modelMap.put("listaDificiles", listaPartidasDificiles);
        modelMap.put("nombreUsuario" , nombreUsuario);


        return new ModelAndView("dificultad",modelMap);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/mercado")
    public ModelAndView mercado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null){
            String nombreUsuario = (String) session.getAttribute("Usuario");
            ModelMap modelMap = new ModelMap();
            modelMap.put("nombreUsuario" , nombreUsuario);

            return new ModelAndView("mercado", modelMap);

        }
        return new ModelAndView("redirect:/login");

    }

    @RequestMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

}