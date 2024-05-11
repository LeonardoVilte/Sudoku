package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioJuego;
import com.tallerwebi.dominio.Sudoku;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class ServicioJuegoImpl implements ServicioJuego {

    @Override
    public Sudoku crearYGuardarSudoku() {
        Sudoku sudoku = new Sudoku();
        Integer[][] tablero = sudoku.getTablero();
        limpiarTablero(tablero);
        sudoku.setDificultad(1);
        sudoku.setTablero(crearDatosParaLaMatriz(sudoku.getDificultad(), tablero));

        return sudoku;
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
    private boolean estaCompleto(Integer[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == 0) {
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
        for (int i = 0; i < tablero.length; i++) {
            if (tablero[i][j]==valor) {
                return false;
            }
        }
        return true;
    }


}
