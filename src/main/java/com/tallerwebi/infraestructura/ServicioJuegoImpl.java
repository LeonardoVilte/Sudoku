package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
@Service
@Transactional
public class ServicioJuegoImpl implements ServicioJuego {

    private RepositorioJuego repositorioJuego;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioJuegoImpl (RepositorioJuego repositorioJuego, RepositorioUsuario repositorioUsuario){
        this.repositorioJuego = repositorioJuego;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Sudoku crearYGuardarSudoku(Integer dificultad) {

        Sudoku sudoku = new Sudoku();
        Integer[][] tablero = sudoku.getTablero();
        sudoku.setDificultad(dificultad);
        sudoku.setTablero(crearDatosParaLaMatriz(sudoku.getDificultad(), tablero));
        sudoku.setTablero(tablero);

        repositorioJuego.guardarSudoku(sudoku);

        return sudoku;
    }
    @Override
    public Partida crearPartidaConSudokuYUsuario(Sudoku sudoku,String emailUsuario) {

        Usuario usuarioEncontrado = repositorioUsuario.buscar(emailUsuario);

        Partida partida = new Partida();
        partida.setSudoku(sudoku);
        partida.setUsuario(usuarioEncontrado);
        repositorioJuego.guardarPartida(partida);

        return partida;
    }

    @Override
    public Partida buscarPartidaActual(Long idPartidaActual) {
        return this.repositorioJuego.buscarPartidaPorId(idPartidaActual);
    }

    @Override
    public void guardarTiemposEnElUsuario(String email, Long tiempoResuelto) {
        Usuario usuarioBuscado = this.repositorioUsuario.buscarPorEmail(email);
        if(usuarioBuscado != null){
            usuarioBuscado.setHorasJugadas(usuarioBuscado.getHorasJugadas()+tiempoResuelto);
        }

    }

    public void limpiarTablero(Integer[][] tablero){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                tablero[i][j] = 0;
            }
        }
    }

    @Override
    public Integer[][] crearDatosParaLaMatriz(Integer dificultad, Integer[][] tablero) {
        limpiarTablero(tablero);
        Random ran = new Random();
        //genera numeros aleatorios en el 1er cuadrante
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int num = ran.nextInt(9) + 1;
                if(validarCuadrado(i,j, num, tablero)) {
                    tablero[i][j] = num;
                }else{
                    j--;
                }
            }
        }
        //genera numeros aleatorios en el 2do cuadrante
        for(int i = 3; i < 6; i++){
            for(int j = 3; j < 6; j++){
                int num = ran.nextInt(9) + 1;
                if(validarCuadrado(i,j, num, tablero)) {
                    tablero[i][j] = num;
                }else{
                    j--;
                }
            }
        }
        //genera numeros aleatorios en el 3er cuadrante
        for(int i = 6; i < 9; i++){
            for(int j = 6; j < 9; j++){
                int num = ran.nextInt(9) + 1;
                if(validarCuadrado(i,j, num, tablero)) {
                    tablero[i][j] = num;
                }else{
                    j--;
                }
            }
        }

        resolverTablero(tablero);

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                int aux = j;
                int rand = ran.nextInt(dificultad+1);
                j+=rand;
                for (int k = aux; k < j && k<tablero.length; k++) {
                    tablero[i][k] = 0;
                }

            }
        }
    return tablero;
    }

    public boolean resolverTablero(Integer[][] tablero){
        // Verificar si el tablero está completo
        if (estaCompleto(tablero)) {
            return true; // Tablero completo y válido
        }

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if(tablero[i][j] == 0){
                    for(int valor = 1; valor <= 9; valor++){
                        if(validarFila(i, valor, tablero) && validarCol(j, valor, tablero) && validarCuadrado(i, j, valor, tablero)){
                            tablero[i][j] = valor;
                            // Llamar recursivamente con el nuevo valor asignado
                            if(resolverTablero(tablero)){
                                return true;
                            }
                            // Backtracking: si la recursión falla, deshacer el cambio
                            tablero[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public Integer[][] sudokuResuelto(Integer[][] tablero) {
        resolverTablero(tablero);
        return tablero;
    }

    public boolean estaCompleto(Integer[][] tablero) {
        for (Integer[] integers : tablero) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (integers[j] == 0) {
                    return false; // falta
                }
            }
        }
        return true;//completo
    }

    public boolean validarCuadrado(int i, int j, int valor, Integer[][] tablero){
        int posicionX = cuadradoActual(i);
        int posicionY = cuadradoActual(j);
        for (int k = posicionX-3; k < posicionX; k++) {
            for (int l = posicionY-3; l < posicionY; l++) {
                if(tablero[k][l]==valor){
                    return false;
                }
            }
        }
        return true;
    }

    public int cuadradoActual(int posicion){
        if(posicion <= 2){
            return 3;
        }else if(posicion <=5){
            return 6;
        }else return 9;
    }

    public boolean validarFila(int i, int valor, Integer[][] tablero){
        for (int j = 0; j < tablero[0].length; j++) {
            if (tablero[i][j]==valor) {
                return false;
            }
        }
        return true;
    }

    public boolean validarCol(int j, int valor, Integer[][] tablero){
        for (Integer[] integers : tablero) {
            if (integers[j] == valor) {
                return false;
            }
        }
        return true;
    }
}
