package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class ControladorRanking {
    private ServicioRanking servicioRanking;
    @Autowired
    public ControladorRanking(ServicioRanking servicioRanking){
        this.servicioRanking= servicioRanking;
    }

    @RequestMapping("/ranking")
    public ModelAndView ranking() {
        //ArrayList usuarios = this.servicioRanking.traerTablaUsuarios();
        return new ModelAndView("ranking");
    }



}
