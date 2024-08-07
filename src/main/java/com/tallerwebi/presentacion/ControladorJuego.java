package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
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
            String nombreUsuario = (String) session.getAttribute("Usuario");
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
            modelAndView.addObject("nombreUsuario", nombreUsuario);
            modelAndView.addObject("monedas", monedas);

            iniciarCronometroSudoku(session);
            return modelAndView;
        } else {
            return new ModelAndView("redirect:login");
        }
    }

    private LocalTime extraerTiempoDeResolucion(HttpSession session) {
        LocalTime tiempoInicio = (LocalTime) session.getAttribute("tiempoInicio");
        LocalTime tiempoPausa = (LocalTime) session.getAttribute("tiempoPausa");
        Long tiempoTotalPausas = (Long) session.getAttribute("tiempoTotalPausas");

        if (tiempoInicio == null) {
            tiempoInicio = LocalTime.now();
            session.setAttribute("tiempoInicio", tiempoInicio);
        }

        if (tiempoTotalPausas == null) {
            tiempoTotalPausas = 0L;
            session.setAttribute("tiempoTotalPausas", tiempoTotalPausas);
        }

        LocalTime tiempoActual = LocalTime.now();
        Long tiempoTranscurrido = Duration.between(tiempoInicio, tiempoActual).getSeconds() - tiempoTotalPausas;

        return LocalTime.ofSecondOfDay(tiempoTranscurrido);
    }

    @RequestMapping(value = "/Resultad0", method = RequestMethod.GET)
    public ModelAndView mostrarResultado(HttpServletRequest request, @RequestParam("resuelto") boolean resuelto, @RequestParam("tiempo") String tiempoResuelto) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String emailUsuario = (String) session.getAttribute("email");
            Long idPartidaActual = (Long) session.getAttribute("idPartidaActual");
            String nombreUsuario = (String) session.getAttribute("Usuario");

            LocalTime tiempoResueltoLocalTime = LocalTime.parse(tiempoResuelto);
            Long tiempoResueltoEnLong = tiempoToLong(tiempoResueltoLocalTime);

            servicioJuego.guardarTiemposEnLaPartida(idPartidaActual, tiempoResueltoLocalTime, resuelto);
            this.servicioJuego.guardarTiemposEnElUsuario(emailUsuario, tiempoResueltoEnLong);

            session.removeAttribute("idPartidaActual");

            Usuario usuarioBuscado = this.servicioUsuario.obtenerUsuarioPorEmail(emailUsuario);
            ModelMap modelMap = new ModelMap();
            modelMap.put("monedas", usuarioBuscado.getMonedas());
            modelMap.put("tiempoResuelto", tiempoResuelto);
            modelMap.put("nombreUsuario" , nombreUsuario);

            String mensajeEnResultado = "Te has rendido, pero aca tienes tu tiempo jugado";
            if(resuelto){
                mensajeEnResultado = "Has completado el sudoku, FELICIDADES este es tu tiempo";
            }
            modelMap.put("mensajeResultado", mensajeEnResultado);


            return new ModelAndView("Resultad0", modelMap);
        } else {
            return new ModelAndView("redirect:login");
        }
    }

    private void iniciarCronometroSudoku(HttpSession session) {
        Long tiempoInicial = System.currentTimeMillis();

        if (session.getAttribute("tiempoInicial") != null) {
            session.removeAttribute("tiempoInicial");
        }
        session.setAttribute("tiempoInicial", tiempoInicial);
        session.setAttribute("tiempoPausado", 0L);
    }
    @PostMapping("/pausar")
    public ResponseEntity<String> pausarJuego(HttpSession session) {
        pausarCronometroSudoku(session);
        return ResponseEntity.ok("El juego ha sido pausado en el backend.");
    }

    @PostMapping("/reanudar")
    public ResponseEntity<String> reanudarJuego(HttpSession session) {
        reanudarCronometroSudoku(session);
        return ResponseEntity.ok("El juego ha sido reanudado en el backend.");
    }

    @PostMapping("/usarAyuda")
    public ResponseEntity<String> usarAyuda(HttpSession session){
        String nombreUsuario = (String)session.getAttribute("Usuario");

        Usuario usuarioBuscado = this.servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario);
        Boolean resultado = this.servicioJuego.usarAyuda(usuarioBuscado);

        if(resultado){
            return ResponseEntity.ok("Ayuda usada exitosamente");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tienes ayudas disponibles");
        }
    }
    @PostMapping("/usarPista")
    public ResponseEntity<String> usarPista(HttpSession session){
        String nombreUsuario = (String) session.getAttribute("Usuario");

        Usuario usuarioBuscado = this.servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario);
        Boolean resultado = this.servicioJuego.usarPista(usuarioBuscado);
        Integer pistasDisponibles = usuarioBuscado.getPistasDisponibles();

        if(resultado){
            return ResponseEntity.ok(String.format("Te quedan: %d pistas", pistasDisponibles));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tienes pistas disponibles");
        }
    }

    private void pausarCronometroSudoku(HttpSession session) {
        Long tiempoDeLaPausa = System.currentTimeMillis();
        session.setAttribute("tiempoDeLaPausa", tiempoDeLaPausa);
    }

    private void reanudarCronometroSudoku(HttpSession session) {
        Long tiempoPausado = (Long) session.getAttribute("tiempoPausado");

        Long tiempoDeLaPausa = (Long) session.getAttribute("tiempoDeLaPausa");
        tiempoPausado += System.currentTimeMillis() - tiempoDeLaPausa;

        session.removeAttribute("tiempoDeLaPausa");
        session.setAttribute("tiempoPausado", tiempoPausado);
    }

    private Long extraerTiempoEnLong(HttpSession session) {
        Long tiempoInicial = (Long) session.getAttribute("tiempoInicial");
        Long tiempoPausado = (Long) session.getAttribute("tiempoPausado");
        return (tiempoInicial != null ? System.currentTimeMillis() - tiempoInicial : 0) + tiempoPausado;
    }

    private Long tiempoToLong(LocalTime tiempo) {
        return tiempo.toSecondOfDay() * 1000L;
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