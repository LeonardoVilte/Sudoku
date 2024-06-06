package com.tallerwebi.dominio;

public interface RepositorioJuego {

    void guardarSudoku(Sudoku sudoku);

    Sudoku buscarSudokuNoResuelto(Long idUsuario);
}
