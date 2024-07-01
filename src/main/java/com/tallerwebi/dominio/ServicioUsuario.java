package com.tallerwebi.dominio;

public interface ServicioUsuario {
    Usuario obtenerUsuarioPorEmail(String email);
    void actualizarUsuario(Usuario usuario);

    boolean siUsuarioExiste(String nombreUsuario);

    Usuario obtenerUsuarioPorNombre(String nombreUsuario);
}
