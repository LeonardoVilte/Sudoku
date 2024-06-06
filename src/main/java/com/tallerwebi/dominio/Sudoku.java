package com.tallerwebi.dominio;

import javax.persistence.*;
import java.sql.Clob;
import java.util.List;
@Entity
public class Sudoku {
    public static final Integer SUB_SIZE = 3;
    public static final Integer SIZE = 9;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private Integer[][] tablero = new Integer[SIZE][SIZE];
    @Lob
    @Column(name = "tablero", columnDefinition = "CLOB")
    private String tableroAlmacenable;
    private Integer dificultad;
    @OneToMany(mappedBy = "sudoku")
    private List<Partida> partidas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer[][] getTablero() {
        return tablero;
    }

    public void setTablero(Integer[][] tablero) {
        this.tablero = tablero;
        this.tableroAlmacenable = convertirMatrizAString(tablero);
    }

    private String convertirMatrizAString(Integer[][] tablero) {
        StringBuilder builder = new StringBuilder();

        for (Integer[] fila : tablero) {
            for (int i = 0; i < fila.length; i++) {
                builder.append(fila[i]);
                if (i < fila.length - 1) {
                    builder.append(",");
                }
            }
            builder.append(";");
        }
        return builder.toString();
    }

    private Integer[][] convertirStringAMatriz(String tableroEnString){
        String[] filas = tableroEnString.split(";");
        Integer[][] matriz = new Integer[filas.length][];

        for (int i = 0; i < filas.length; i++) {
            String[] elementos = filas[i].split(",");
            matriz[i] = new Integer[elementos.length];

            for (int j = 0; j < elementos.length; j++) {
                matriz[i][j] = Integer.valueOf(elementos[j]);
            }
        }
        return matriz;

    }

    public Integer getDificultad() {
        return dificultad;
    }

    public void setDificultad(Integer dificultad) {
        this.dificultad = dificultad;
    }

    public String getTableroAlmacenable() {
        return tableroAlmacenable;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }
}
