package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioJuego {

    void guardarSudoku(Sudoku sudoku);

    Sudoku buscarSudokuNoResuelto(Long idUsuario);

    void guardarPartida(Partida partida);

    Partida buscarPartidaPorId(Long idPartida);

    List<Partida> traerPartidasPorDificultad(int dificultad);
}
