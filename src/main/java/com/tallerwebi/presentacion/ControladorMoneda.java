package com.tallerwebi.presentacion;

import ch.qos.logback.classic.Logger;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.MercadoPagoService;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ControladorMoneda.class);

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
    public RedirectView comprarMonedas(@RequestParam("cantidad") int cantidad) throws Exception {
        try {
            Preference preference = servicioMercadoPago.crearPreferencia(cantidad);
            if (preference != null) {
                String preferenceId = preference.getId();
                String redirectUrl = "https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=" + preferenceId;
                return new RedirectView(redirectUrl);
            }
        } catch (MPApiException e) {
            logger.error("Error al crear la preferencia de Mercado Pago", e);

            MPResponse response = e.getApiResponse();
            if (response != null) {
                logger.error("Código de estado: {}", response.getStatusCode());
                logger.error("Contenido: {}", response.getContent());
            }
        } catch (MPException e) {
            logger.error("Error general de Mercado Pago", e);
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

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView paymentSuccess(HttpServletRequest request,
                                       @RequestParam("collection_id") String collectionId,
                                       @RequestParam("collection_status") String collectionStatus,
                                       @RequestParam("payment_id") String paymentId,
                                       @RequestParam("status") String status,
                                       @RequestParam("external_reference") String externalReference,
                                       @RequestParam("payment_type") String paymentType,
                                       @RequestParam("merchant_order_id") String merchantOrderId,
                                       @RequestParam("preference_id") String preferenceId,
                                       @RequestParam("site_id") String siteId,
                                       @RequestParam("processing_mode") String processingMode,
                                       @RequestParam(value = "merchant_account_id", required = false) String merchantAccountId) throws UsuarioNoEncontrado {

        ModelAndView modelAndView = new ModelAndView("payment-success");

        try {
            int cantidadMonedasCompradas = Integer.parseInt(externalReference);

            Usuario usuario = servicioUsuario.obtenerUsuarioActual();
            usuario.setMonedas(usuario.getMonedas() + cantidadMonedasCompradas);
            servicioUsuario.actualizarUsuario(usuario);

            modelAndView.addObject("monedas", usuario.getMonedas());
        } catch (NumberFormatException e) {
            logger.error("Error al parsear la cantidad de monedas compradas", e);
            modelAndView.setViewName("error");
        } catch (UsuarioNoEncontrado e) {
            logger.error("Usuario no encontrado", e);
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/failure", method = RequestMethod.GET)
    public ModelAndView paymentFailure() {
        return new ModelAndView("payment-failure");
    }

    @RequestMapping(value = "/pending", method = RequestMethod.GET)
    public ModelAndView paymentPending() {
        return new ModelAndView("payment-pending");
    }
}