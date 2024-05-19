package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import com.tallerwebi.dominio.ServicioJuego;
import com.tallerwebi.dominio.Sudoku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ControladorJuego {

    private final ServicioJuego servicioJuego ;


    @Autowired
    public ControladorJuego(ServicioJuego servicioJuego){
        this.servicioJuego = servicioJuego;
    }

    @RequestMapping("/jugar")
    public ModelAndView mostrarJuego(@RequestParam("dificultad") int dificultad) {
        Sudoku sudoku = servicioJuego.crearYGuardarSudoku(dificultad);
        String sudokuString = convertirSudokuACadena(sudoku.getTablero());
        String sudokuResueltoString = convertirSudokuACadena(servicioJuego.sudokuResuelto(sudoku.getTablero()));

        ModelAndView modelAndView = new ModelAndView("juego");
        modelAndView.addObject("sudoku", sudokuString);
        modelAndView.addObject("sudokuResuelto", sudokuResueltoString);

        return modelAndView;

    }


    private String convertirSudokuACadena(Integer[][] tablero) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if(tablero[i][j] != 0){
                    sb.append(tablero[i][j]);
                }else{
                    sb.append(" ");
                }
                if (j < tablero[i].length - 1) {
                    sb.append(",");
                }
            }
            if (i < tablero.length - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

}
