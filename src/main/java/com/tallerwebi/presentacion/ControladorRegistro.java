package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {
    @RequestMapping("/Registro")
    public ModelAndView mostrarRegistro() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
    return new ModelAndView("Registro", model);
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
