package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioRanking;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class ControladorRanking {
    private ServicioRanking servicioRanking;
    @Autowired
    public ControladorRanking(ServicioRanking servicioRanking){
        this.servicioRanking= servicioRanking;
    }

    @RequestMapping("/ranking")
    public ModelAndView ranking() {

        List<Usuario> usuarios = this.servicioRanking.traerTablaUsuarios();

        ModelMap model = new ModelMap();
        model.put("listaUsuarios", usuarios);
        return new ModelAndView("ranking", model);
    }

}
