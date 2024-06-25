package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/payment")
public class ControladorPago {

    @RequestMapping("/success")
    public ModelAndView paymentSuccess() {
        ModelAndView modelAndView = new ModelAndView("payment-success");
        // Aquí puedes añadir lógica adicional si es necesario
        return modelAndView;
    }

    @RequestMapping("/failure")
    public ModelAndView paymentFailure() {
        ModelAndView modelAndView = new ModelAndView("payment-failure");
        // Aquí puedes añadir lógica adicional si es necesario
        return modelAndView;
    }

    @RequestMapping("/pending")
    public ModelAndView paymentPending() {
        ModelAndView modelAndView = new ModelAndView("payment-pending");
        // Aquí puedes añadir lógica adicional si es necesario
        return modelAndView;
    }
}
