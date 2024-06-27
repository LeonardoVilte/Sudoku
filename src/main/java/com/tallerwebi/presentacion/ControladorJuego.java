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
import java.util.List;


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

            String emailUsuario = (String) session.getAttribute("email");

            Sudoku sudoku = servicioJuego.crearYGuardarSudoku(dificultad);
            Partida partida = servicioJuego.crearPartidaConSudokuYUsuario(sudoku, emailUsuario, dificultad);

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

    @RequestMapping(value = "/Resultad0", method = RequestMethod.GET)
    public ModelAndView mostrarResultado(HttpServletRequest request,
                                         @RequestParam("resuelto") boolean resuelto)  {
        HttpSession session = request.getSession(false);

        String emailUsuario = (String) session.getAttribute("email");
        // TRAER LA ULTIMA PARTIDA DEL USUARIO
        Long idPartidaActual = (Long)session.getAttribute("idPartidaActual");


        // CALCULAR EL TIEMPO EN EL QUE TARDAR RESOLVER EL SUDOKU
        LocalTime tiempoResuelto = extraerTiempoDeResolucion(session);
        Long tiempoResueltoEnLong = extraerTiempoEnLong(session);

        servicioJuego.guardarTiemposEnLaPartida(idPartidaActual, tiempoResuelto,resuelto);

        this.servicioJuego.guardarTiemposEnElUsuario(emailUsuario, tiempoResueltoEnLong);

        session.removeAttribute("idPartidaActual");

        return new ModelAndView("Resultad0");
    }

    private LocalTime extraerTiempoDeResolucion(HttpSession session) {
        Long tiempoInicial = (Long) session.getAttribute("tiempoInicial");

            long tiempoSudoku = System.currentTimeMillis() - tiempoInicial;

            long segundos = tiempoSudoku / 1000;
            long horas = segundos / 3600;
            long minutos = (segundos % 3600) / 60;
            segundos = segundos % 60;
        return LocalTime.of((int) horas, (int) minutos, (int) segundos);

    }
    private Long extraerTiempoEnLong(HttpSession session){
        Long tiempoInicial = (Long) session.getAttribute("tiempoInicial");
        return System.currentTimeMillis() - tiempoInicial;
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
        Long tiempoInicial = System.currentTimeMillis();

        if(session.getAttribute("tiempoInicial")!=null){
            session.removeAttribute("tiempoInicial");
        }
        session.setAttribute("tiempoInicial", tiempoInicial);
    }
}
