package com.tallerwebi.dominio;

public interface ServicioUsuario {
    Usuario obtenerUsuarioPorEmail(String email);
    void actualizarUsuario(Usuario usuario);
    void actualizarMonedas(String email, Integer cantidad);
    void actualizarPistas(String email, Integer cantidad);
    void actualizarAyudas(String email, Integer cantidad);
}
