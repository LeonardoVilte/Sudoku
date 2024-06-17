package com.tallerwebi.presentacion;

import com.tallerwebi.*;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControladorEconomia {

    @Autowired
    private ServicioUsuario usuarioService;

    @Autowired
    private HistorialJuegosService historialJuegosService;

    @Autowired
    private TransaccionesMercadoService transaccionesMercadoService;

    @RequestMapping(value = "/completarSudoku", method = RequestMethod.POST)
    public String completarSudoku(String dificultad, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioActual();
        int monedasGanadas = 0;

        switch (dificultad) {
            case "facil":
                monedasGanadas = 5;
                break;
            case "intermedio":
                monedasGanadas = 10;
                break;
            case "dificil":
                monedasGanadas = 30;
                break;
        }

        usuario.setMonedas(usuario.getMonedas() + monedasGanadas);
        usuarioService.guardar(usuario);

        HistorialJuegos historial = new HistorialJuegos();
        historial.setUsuarioId(usuario.getId());
        historial.setDificultad(dificultad);
        historial.setMonedasGanadas(monedasGanadas);
        historial.setFechaHora(new Date());
        historialJuegosService.guardar(historial);

        model.addAttribute("usuario", usuario);
        return "sudokuCompletado";
    }

    @RequestMapping(value = "/comprarPista", method = RequestMethod.POST)
    public String comprarPista(Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioActual();
        if (usuario.getMonedas() >= 3) {
            usuario.setMonedas(usuario.getMonedas() - 3);
            usuario.setPistas(usuario.getPistas() + 1);
            usuarioService.guardar(usuario);

            TransaccionesMercado transaccion = new TransaccionesMercado();
            transaccion.setUsuarioId(usuario.getId());
            transaccion.setTipo("Pista");
            transaccion.setCantidad(1);
            transaccion.setMonedasGastadas(3);
            transaccion.setFechaHora(new Date());
            transaccionesMercadoService.guardar(transaccion);
        }
        model.addAttribute("usuario", usuario);
        return "mercado";
    }

    @RequestMapping(value = "/comprarAyuda", method = RequestMethod.POST)
    public String comprarAyuda(Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioActual();
        if (usuario.getMonedas() >= 5) {
            usuario.setMonedas(usuario.getMonedas() - 5);
            usuario.setAyudas(usuario.getAyudas() + 1);
            usuarioService.guardar(usuario);

            TransaccionesMercado transaccion = new TransaccionesMercado();
            transaccion.setUsuarioId(usuario.getId());
            transaccion.setTipo("Ayuda");
            transaccion.setCantidad(1);
            transaccion.setMonedasGastadas(5);
            transaccion.setFechaHora(new Date());
            transaccionesMercadoService.guardar(transaccion);
        }
        model.addAttribute("usuario", usuario);
        return "mercado";
    }
}
