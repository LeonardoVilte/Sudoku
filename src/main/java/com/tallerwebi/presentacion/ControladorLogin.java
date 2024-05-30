package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContrasenasDistintas;
import com.tallerwebi.dominio.excepcion.NombreDeUsuarioRepetido;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }


    // Metodos del login
    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado;
        usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());

        if (usuarioBuscado != null) {

            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            request.getSession().setAttribute("Usuario", usuarioBuscado.getNombre());
            request.getSession().setAttribute("mail", usuarioBuscado.getEmail());
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }

    //Metodos del Registro
    @RequestMapping("/Registro")
    public ModelAndView mostrarRegistro() {
        ModelMap model = new ModelMap();
        model.put("DatosRegistroDTO", new DatosRegistroDTO());
        return new ModelAndView("Registro", model);
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("DatosRegistro") DatosRegistroDTO datosRegistroDTO) {
        ModelMap model = new ModelMap();
        try {
            servicioLogin.registrar(datosRegistroDTO);
        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
            model.put("DatosRegistroDTO", new DatosRegistroDTO());
            return new ModelAndView("Registro", model);

        } catch (ContrasenasDistintas e) {
            model.put("error", "Las contraseñas no son iguales");
            model.put("DatosRegistroDTO", new DatosRegistroDTO());
            return new ModelAndView("Registro", model);

        } catch (NombreDeUsuarioRepetido e) {
            model.put("error", "Nombre de usuario repetido");
            model.put("DatosRegistroDTO", new DatosRegistroDTO());
            return new ModelAndView("Registro", model);

        } catch (Exception e) {
            model.put("error", "Error al registrar el nuevo usuario");
            model.put("DatosRegistroDTO", new DatosRegistroDTO());
            return new ModelAndView("Registro", model);
        }

        return new ModelAndView("redirect:/login");
    }
}

