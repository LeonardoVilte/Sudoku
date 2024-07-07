package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.presentacion.MercadoPagoNotification;

import java.time.LocalTime;

public interface ServicioUsuario {
    Usuario obtenerUsuarioPorEmail(String email);
    void actualizarUsuario(Usuario usuario);

    boolean siUsuarioExiste(String nombreUsuario);

    Usuario obtenerUsuarioPorNombre(String nombreUsuario);
    Usuario obtenerUsuarioActual() throws UsuarioNoEncontrado;
    void actualizarMonedas(MercadoPagoNotification notification);

    LocalTime obtenerTiempoJugadoEnTodasLasPartidas(Usuario usuario);

    Integer obtenerCantidadDePartidasCompletadas(Usuario usuario);
}
