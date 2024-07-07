package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.presentacion.MercadoPagoNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return repositorioUsuario.buscarPorEmail(email);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public boolean siUsuarioExiste(String nombreUsuario) {
        Usuario usuarioEncontrado = this.repositorioUsuario.buscarUsuarioPorNombre(nombreUsuario);
        return usuarioEncontrado != null;

    }

    @Override
    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        return this.repositorioUsuario.buscarUsuarioPorNombre(nombreUsuario);
    }

    public void actualizarMonedas(MercadoPagoNotification notification) {
        if (notification.getStatus().equals("approved")) {
            Usuario usuario = obtenerUsuarioPorEmail(notification.getEmail());
            usuario.setMonedas(usuario.getMonedas() + notification.getCantidadMonedas());
            actualizarUsuario(usuario);
        }
    }

    @Override
    public LocalTime obtenerTiempoJugadoEnTodasLasPartidas(Usuario usuario) {
        return this.repositorioUsuario.obtenerTiempoJugadoDelUsuario(usuario);
    }

    @Override
    public Usuario obtenerUsuarioActual() throws UsuarioNoEncontrado {
        Usuario usuario = repositorioUsuario.buscarPorEmail("test@unlam.edu.ar");
        if (usuario == null) {
            throw new UsuarioNoEncontrado();
        }
        return usuario;
    }
}
