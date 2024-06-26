package com.tallerwebi.presentacion;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.MercadoPagoService;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorMoneda {

    private final MercadoPagoService servicioMercadoPago;
    private final ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorMoneda(MercadoPagoService servicioMercadoPago, ServicioUsuario servicioUsuario) {
        this.servicioMercadoPago = servicioMercadoPago;
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping(value = "/comprar", method = RequestMethod.GET)
    public ModelAndView vistaComprarMonedas(HttpServletRequest request) throws UsuarioNoEncontrado {
        ModelAndView modelAndView = new ModelAndView("comprar-monedas");
        Usuario usuario = servicioUsuario.obtenerUsuarioActual();
        modelAndView.addObject("monedas", usuario.getMonedas());
        return modelAndView;
    }

    @RequestMapping(value = "/comprar", method = RequestMethod.POST)
    public RedirectView comprarMonedas(@RequestParam("cantidad") int cantidad) {
        try {
            Preference preference = servicioMercadoPago.crearPreferencia(cantidad);
            if (preference != null) {
                String preferenceId = preference.getId();
                String redirectUrl = "https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=" + preferenceId;
                return new RedirectView(redirectUrl);
            }
        } catch (Exception e) {
            // Manejar la excepción de Mercado Pago aquí
            e.printStackTrace(); // Opcional: Imprimir el stack trace para depuración
            return new RedirectView("/error"); // Redirigir a una página de error adecuada
        }
        return new RedirectView("/error");
    }

    @RequestMapping(value = "/vista", method = RequestMethod.GET)
    public ModelAndView vistaMonedas() throws UsuarioNoEncontrado {
        ModelAndView modelAndView = new ModelAndView("vista-monedas");
        Usuario usuario = servicioUsuario.obtenerUsuarioActual();
        modelAndView.addObject("monedas", usuario.getMonedas());
        return modelAndView;
    }
}
