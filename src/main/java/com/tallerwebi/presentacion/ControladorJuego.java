package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Partida;
import org.springframework.stereotype.Controller;
import com.tallerwebi.dominio.ServicioJuego;
import com.tallerwebi.dominio.Sudoku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;


@Controller
public class ControladorJuego {

    private final ServicioJuego servicioJuego;


    @Autowired
    public ControladorJuego(ServicioJuego servicioJuego){
        this.servicioJuego = servicioJuego;
    }

    @RequestMapping("/jugar")
    public ModelAndView mostrarJuego(@RequestParam("dificultad") int dificultad, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) {

            String email = (String) session.getAttribute("email");

            Sudoku sudoku = servicioJuego.crearYGuardarSudoku(dificultad);
            Partida partida = servicioJuego.crearPartidaConSudokuYUsuario(sudoku, email);

            session.setAttribute("idSudoku", sudoku.getId());
            session.setAttribute("idPartidaActual", partida.getId());

            String sudokuString = convertirSudokuACadena(sudoku.getTablero());
            String sudokuResueltoString = convertirSudokuACadena(servicioJuego.sudokuResuelto(sudoku.getTablero()));

            ModelAndView modelAndView = new ModelAndView("juego");
            modelAndView.addObject("sudoku", sudokuString);
            modelAndView.addObject("sudokuResuelto", sudokuResueltoString);

            iniciarCronometroSudoku(session);

            return modelAndView;
        }else{
            return new ModelAndView("redirect:login");
        }
    }

    @RequestMapping("/Resultad0")
    public ModelAndView mostrarResultado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // TRAER LA ULTIMA PARTIDA DEL USUARIO
        Long idPartidaActual = (Long)session.getAttribute("idPartidaActual");
        Partida partidaActual = servicioJuego.buscarPartidaActual(idPartidaActual);

        // CALCULAR EL TIEMPO EN EL QUE TARDAR RESOLVER EL SUDOKU
        LocalTime tiempoResuelto = ExtraerTiempoDeResolucion(session);

        partidaActual.setTiempo(tiempoResuelto);

        session.removeAttribute("idPartidaActual");
        session.removeAttribute("tiempoInicial");


        return new ModelAndView("Resultad0");
    }

    private LocalTime ExtraerTiempoDeResolucion(HttpSession session) {
        Long tiempoInicial = (Long) session.getAttribute("tiempoInicial");
        long tiempoSudoku = System.currentTimeMillis() - tiempoInicial;

        long segundos = tiempoSudoku / 1000;
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        segundos = segundos % 60;

        return LocalTime.of((int) horas, (int) minutos, (int) segundos);

    }


    private String convertirSudokuACadena (Integer[][]tablero){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero[i].length; j++) {
                    if (tablero[i][j] != 0) {
                        sb.append(tablero[i][j]);
                    } else {
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

    private void iniciarCronometroSudoku(HttpSession session){
        session.setAttribute("tiempoInicial", System.currentTimeMillis());
    }
}
