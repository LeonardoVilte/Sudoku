package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
    Usuario buscarPorEmail(String email);

    Usuario  buscarUsuarioPorNombre(String nombre);

    List<Usuario> traerRankingUsuarios();

    void actualizarDatosDeLaPartida(Usuario usuario, Long tiempoResuelto);
}

