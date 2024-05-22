package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import com.tallerwebi.dominio.ServicioJuego;
import com.tallerwebi.dominio.Sudoku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class ControladorJuego {

    private final ServicioJuego servicioJuego;

    @Autowired
    public ControladorJuego(ServicioJuego servicioJuego){
        this.servicioJuego = servicioJuego;
    }



    @RequestMapping("/jugar")
    public ModelAndView mostrarJuego(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("ROL") != null){
            Sudoku sudoku = servicioJuego.crearYGuardarSudoku();
        String sudokuString = convertirSudokuACadena(sudoku.getTablero());
        ModelMap modelMap = new ModelMap();
        modelMap.put("sudoku", sudokuString);
        return new ModelAndView("juego", modelMap);

        }else{
            return new ModelAndView("redirect:login");
        }
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

//    ("/resolver-sudoku")
//    public Integer[][] resolverSudoku() {
//        Sudoku sudoku = servicioJuego.crearYGuardarSudoku();
//        sudoku.resolverTablero();
//        return sudoku.getTablero();
//    }
}
