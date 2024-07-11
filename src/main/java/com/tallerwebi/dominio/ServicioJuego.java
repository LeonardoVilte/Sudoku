package com.tallerwebi.dominio;

import java.time.LocalTime;
import java.util.List;

public interface ServicioJuego {

    Sudoku crearYGuardarSudoku(Integer dificultad);

    Integer[][] crearDatosParaLaMatriz(Integer dificultad,Integer[][] tablero);

    Integer[][] sudokuResuelto(Integer[][] tablero);

    boolean estaCompleto(Integer[][] tablero);

    boolean validarCuadrado(int i, int j, int valor, Integer[][] tablero);

    int cuadradoActual(int posicion);

    boolean validarFila(int i, int valor, Integer[][] tablero);

    boolean validarCol(int j, int valor, Integer[][] tablero);

    void limpiarTablero(Integer[][] tablero);

    boolean resolverTablero(Integer[][] tablero);

    Partida crearPartidaConSudokuYUsuario(Sudoku sudoku,String emailUsuario, Integer dificultad);

    Partida buscarPartidaActual(Long idPartidaActual);

    void guardarTiemposEnElUsuario(String email , Long tiempoResuelto);

    List<Partida> traer3MejoresTiemposPorDificultad(int i);

    void guardarTiemposEnLaPartida(Long idPartidaActual, LocalTime tiempoResuelto, boolean resuelto);

    Boolean usarAyuda(Usuario usuarioBuscado);
}
