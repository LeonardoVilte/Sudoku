package com.tallerwebi.presentacion;

import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.MercadoPagoService;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMoneda {

    @Autowired
    private MercadoPagoService servicioMercadoPago;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @RequestMapping("/comprar")
    public ModelAndView comprarMonedas() {

        ModelAndView modelAndView = new ModelAndView("comprar-monedas");
        int cantidad = 0;
        Preference preference = servicioMercadoPago.crearPreferencia(cantidad);
        if (preference != null) {
            modelAndView.addObject("preferenceId", preference.getId());
        }
        return modelAndView;
    }

    @RequestMapping("/vista")
    public ModelAndView vistaMonedas() {
        ModelAndView modelAndView = new ModelAndView("vista-monedas");
        Usuario usuario = servicioUsuario.obtenerUsuarioActual();
        modelAndView.addObject("monedas", usuario.getMonedas());
        return modelAndView;
    }
}
