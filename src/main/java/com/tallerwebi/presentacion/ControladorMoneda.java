package com.tallerwebi.presentacion;

import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/monedas")
public class ControladorMoneda {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @RequestMapping("/comprar")
    public ModelAndView comprarMonedas(@RequestParam("cantidad") int cantidad, Model model) {
        Preference preference = mercadoPagoService.crearPreferencia(cantidad);
        if (preference != null) {
            model.addAttribute("preferenceId", preference.getId());
        }
        return new ModelAndView("comprar-monedas");
    }
}
