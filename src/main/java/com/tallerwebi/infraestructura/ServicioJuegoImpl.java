package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioJuego;
import com.tallerwebi.dominio.Sudoku;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class ServicioJuegoImpl implements ServicioJuego {

    @Override
    public Sudoku crearYGuardarSudoku() {
        Integer[][] matriz = crearDatosParaLaMatriz();
        Sudoku sudoku = new Sudoku();
        sudoku.setDificultad("1");
        sudoku.setTablero(matriz);

        return sudoku;
    }

    @Override
    public Integer[][] crearDatosParaLaMatriz() {
        Integer[][] matriz = new Integer[Sudoku.SIZE][Sudoku.SIZE];
        Random ran = new Random();

        for(int fila = 0; fila < Sudoku.SIZE; fila++){
            for(int columna = 0; columna<Sudoku.SIZE; columna++){
                int num;
                do{
                    num = ran.nextInt(Sudoku.SIZE) + 1;
                }while(!validarTabla(matriz, fila,columna, num));
                matriz[fila][columna] = num;
            }
        }
        return matriz;
    }

    private Boolean validarTabla(Integer[][] tabla, int fila, int columna, int num){

        if(tabla[0][0]==null){
            return false;
        }

        for(int i = 0; i < Sudoku.SIZE; i++){
            if(tabla[fila][i] == num){
                return false;
            }
        }
        for(int j = 0; j < Sudoku.SIZE; j++) {
            if (tabla[j][columna] == num) {
                return false;
            }
        }

        int subgrillaFilaInicio = fila - fila % Sudoku.SUB_SIZE;
        int subgrillaColumnaInicio = columna - columna % Sudoku.SUB_SIZE;
        for(int n = subgrillaFilaInicio; n < subgrillaFilaInicio + Sudoku.SUB_SIZE; n++){
            for(int a = subgrillaColumnaInicio; a < subgrillaColumnaInicio + Sudoku.SUB_SIZE; a++){
                if(tabla[n][a] == num){
                    return false;
                }
            }
        }

    return true;
    }


}
