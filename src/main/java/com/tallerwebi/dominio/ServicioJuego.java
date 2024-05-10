package com.tallerwebi.dominio;

public interface ServicioJuego {

    Sudoku crearYGuardarSudoku();


    Integer[][] crearDatosParaLaMatriz(Integer dificultad, Integer[][] tablero);
}
