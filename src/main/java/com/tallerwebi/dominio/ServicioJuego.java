package com.tallerwebi.dominio;

public interface ServicioJuego {

    Sudoku crearYGuardarSudoku(Integer dificultad);


    Integer[][] crearDatosParaLaMatriz(Integer dificultad,Integer[][] tablero);

    Integer[][] sudokuResuelto(Integer[][] tablero);

}
