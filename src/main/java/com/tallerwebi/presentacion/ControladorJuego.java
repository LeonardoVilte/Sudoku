package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import com.tallerwebi.dominio.ServicioJuego;
import com.tallerwebi.dominio.Sudoku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ControladorJuego {

    private ServicioJuego servicioJuego;

    @Autowired
    public ControladorJuego(ServicioJuego servicioJuego){
        this.servicioJuego = servicioJuego;
    }


    @RequestMapping("/jugar")
    public ModelAndView mostrarJuego() {

        /*Sudoku sudoku = this.servicioJuego.crearYGuardarSudoku();
        ModelMap modelMap = new ModelMap();

        modelMap.put("sudoku", sudoku);
        return new ModelAndView("juego", modelMap);
        */

        return  new ModelAndView("juego");
    }
}