package com.tallerwebi.dominio;

public class Sudoku {
    //public static final int SUB_SIZE = 3;
    //public static final int SIZE = 9;
    private Integer id;
    private Integer[][] tablero;
    //private String dificultad;

    public Sudoku(){
        tablero = new Integer[][]{
                {0,6,0,1,0,4,0,5,0},
                {0,0,8,3,0,5,6,0,0},
                {2,0,0,0,0,0,0,0,1},
                {8,0,0,4,0,7,0,0,6},
                {0,0,6,0,0,0,3,0,0},
                {7,0,0,9,0,1,0,0,4},
                {5,0,0,0,0,0,0,0,2},
                {0,0,7,2,0,6,9,0,0},
                {0,4,0,5,0,8,0,7,0},
        };
    }

    public boolean resolverTablero(){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if(tablero[i][j]==0){
                    for(int valor = 1; valor < 9; valor++){
                        if(validarFila(i,valor) && validarCol(j, valor) && validarCuadrado(i, j, valor)){
                            tablero[i][j]=valor;
                            //es recursivo porque asigna el valor y sigue con el proximo valor
                            if(resolverTablero()){
                                return true;
                            }
                            //back-traking porque si da falso vuelvo a cero hasta el ultimo valor que si me daba bien en la funcion
                            tablero[i][j]=0;
                        }
                    }return false;
                }
            }
    //como es recursivo solo da true cuando esta la matriz completa diferente de cero
        }return true;
    }

    public boolean validarCuadrado(int i, int j, int valor){
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
    public boolean validarFila(int i, int valor){
        for (int j = 0; j < tablero[0].length; j++) {
            if (tablero[i][j]==valor) {
                return false;
            }
        }
        return true;
    }

    public boolean validarCol(int j, int valor){
        for (int i = 0; i < tablero.length; i++) {
            if (tablero[i][j]==valor) {
                return false;
            }
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer[][] getTablero() {
        return tablero;
    }

    public void setTablero(Integer[][] tablero) {
        this.tablero = tablero;
    }

//    public String getDificultad() {
//        return dificultad;
//    }
//
//    public void setDificultad(String dificultad) {
//        this.dificultad = dificultad;
//    }
}