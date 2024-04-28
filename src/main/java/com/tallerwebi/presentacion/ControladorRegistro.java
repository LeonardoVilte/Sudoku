package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {
    @RequestMapping("/Registro")
    public ModelAndView mostrarRegistro() {

    return new ModelAndView("Registro");
    }
@RequestMapping("/registrar")
    public ModelAndView registrar(@RequestParam("username") String username,
                                  @RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  @RequestParam("c_password") String confirmPassword) {

        //Logica para validar llamar al servicio de registro y
        // luego guardar el usuario en la base de datos.

    return new ModelAndView("home");
}

}
