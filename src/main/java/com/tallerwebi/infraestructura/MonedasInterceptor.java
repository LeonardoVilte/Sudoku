package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MonedasInterceptor implements HandlerInterceptor {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && request.getUserPrincipal() != null) { // Asegurarse de que el usuario está autenticado
            Usuario usuario = servicioUsuario.obtenerUsuarioActual();
            modelAndView.addObject("monedas", usuario.getMonedas());
        }
    }
}
