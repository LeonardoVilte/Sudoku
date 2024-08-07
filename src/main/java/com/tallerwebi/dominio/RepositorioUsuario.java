package com.tallerwebi.dominio;

import java.time.Duration;
import java.time.LocalTime;
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

    LocalTime obtenerTiempoJugadoDelUsuario(Usuario usuario);

    Integer obtenerCantidadDePartidasCompletadas(Usuario usuario);

    LocalTime obtenerTiempoPromedio(Usuario usuario);
}

