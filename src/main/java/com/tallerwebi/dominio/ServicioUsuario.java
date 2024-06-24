package com.tallerwebi.dominio;

public interface ServicioUsuario {
    Usuario obtenerUsuarioPorEmail(String email);
    void actualizarUsuario(Usuario usuario);
    Usuario obtenerUsuarioActual();
}
