package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;

public interface ServicioUsuario {
    Usuario obtenerUsuarioPorEmail(String email);
    void actualizarUsuario(Usuario usuario);
    Usuario obtenerUsuarioActual() throws UsuarioNoEncontrado;
}
