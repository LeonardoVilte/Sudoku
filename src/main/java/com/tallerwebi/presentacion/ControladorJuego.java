package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.List;


@Controller
public class ControladorJuego {

    private final ServicioJuego servicioJuego;
    private final ServicioUsuario servicioUsuario;


    @Autowired
    public ControladorJuego(ServicioJuego servicioJuego, ServicioUsuario servicioUsuario){
        this.servicioJuego = servicioJuego;
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping("/jugar")
    public ModelAndView mostrarJuego(@RequestParam("dificultad") int dificultad, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String emailUsuario = (String) session.getAttribute("email");
            Usuario usuarioBuscado = this.servicioUsuario.obtenerUsuarioPorEmail(emailUsuario);

            Sudoku sudoku = servicioJuego.crearYGuardarSudoku(dificultad);
            Partida partida = servicioJuego.crearPartidaConSudokuYUsuario(sudoku, emailUsuario, dificultad);

            session.setAttribute("idPartidaActual", partida.getId());

            String sudokuString = convertirSudokuACadena(sudoku.getTablero());
            String sudokuResueltoString = convertirSudokuACadena(servicioJuego.sudokuResuelto(sudoku.getTablero()));
            Integer monedas = usuarioBuscado.getMonedas();

            ModelAndView modelAndView = new ModelAndView("juego");
            modelAndView.addObject("sudoku", sudokuString);
            modelAndView.addObject("sudokuResuelto", sudokuResueltoString);
            modelAndView.addObject("monedas", monedas);

            iniciarCronometroSudoku(session);
            return modelAndView;
        } else {
            return new ModelAndView("redirect:login");
        }
    }

    @PostMapping
    @ResponseBody
    public String pausaReanudaJuego(@RequestParam("dificultad") int dificultad, @RequestParam("esPausa") boolean esPausa, HttpSession session) {
        if (esPausa) {
            pausarCronometroSudoku(session);
            return "El juego ha sido pausado en el backend.";
        } else {
            reanudarCronometroSudoku(session);
            return "El juego ha sido reanudado en el backend.";
        }
    }

    @RequestMapping(value = "/Resultad0", method = RequestMethod.GET)
    public ModelAndView mostrarResultado(HttpServletRequest request, @RequestParam("resuelto") boolean resuelto) {
        HttpSession session = request.getSession(false);

        String emailUsuario = (String) session.getAttribute("email");
        Long idPartidaActual = (Long) session.getAttribute("idPartidaActual");

        LocalTime tiempoResuelto = extraerTiempoDeResolucion(session);
        Long tiempoResueltoEnLong = extraerTiempoEnLong(session);

        servicioJuego.guardarTiemposEnLaPartida(idPartidaActual, tiempoResuelto, resuelto);
        this.servicioJuego.guardarTiemposEnElUsuario(emailUsuario, tiempoResueltoEnLong);

        session.removeAttribute("idPartidaActual");

        String email = (String) session.getAttribute("email");
        Usuario usuarioBuscado = this.servicioUsuario.obtenerUsuarioPorEmail(email);
        ModelMap modelMap = new ModelMap();
        modelMap.put("monedas", usuarioBuscado.getMonedas());

        return new ModelAndView("Resultad0", modelMap);
    }

    private void iniciarCronometroSudoku(HttpSession session) {
        Long tiempoInicial = System.currentTimeMillis();

        if (session.getAttribute("tiempoInicial") != null) {
            session.removeAttribute("tiempoInicial");
        }
        session.setAttribute("tiempoInicial", tiempoInicial);
        session.setAttribute("tiempoPausado", 0L);
    }

    private void pausarCronometroSudoku(HttpSession session) {
        Long tiempoInicial = (Long) session.getAttribute("tiempoInicial");
        Long tiempoPausado = (Long) session.getAttribute("tiempoPausado");
        tiempoPausado += System.currentTimeMillis() - tiempoInicial;
        session.setAttribute("tiempoPausado", tiempoPausado);
        session.removeAttribute("tiempoInicial");
    }

    private void reanudarCronometroSudoku(HttpSession session) {
        Long tiempoPausado = (Long) session.getAttribute("tiempoPausado");
        Long tiempoInicial = System.currentTimeMillis() - tiempoPausado;
        session.setAttribute("tiempoInicial", tiempoInicial);
    }

    private LocalTime extraerTiempoDeResolucion(HttpSession session) {
        Long tiempoInicial = (Long) session.getAttribute("tiempoInicial");
        Long tiempoPausado = (Long) session.getAttribute("tiempoPausado");
        long tiempoSudoku = (tiempoInicial != null ? System.currentTimeMillis() - tiempoInicial : 0) + tiempoPausado;

        long segundos = tiempoSudoku / 1000;
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        segundos = segundos % 60;
        return LocalTime.of((int) horas, (int) minutos, (int) segundos);
    }

    private Long extraerTiempoEnLong(HttpSession session) {
        Long tiempoInicial = (Long) session.getAttribute("tiempoInicial");
        Long tiempoPausado = (Long) session.getAttribute("tiempoPausado");
        return (tiempoInicial != null ? System.currentTimeMillis() - tiempoInicial : 0) + tiempoPausado;
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
}
