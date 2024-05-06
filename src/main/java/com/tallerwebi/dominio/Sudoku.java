package com.tallerwebi.dominio;

public class Sudoku {
    public static final int SUB_SIZE = 3;
    public static final int SIZE = 9;
    private Integer id;
    private Integer[][] tablero;
    private String dificultad;

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

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}