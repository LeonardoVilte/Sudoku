package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn (name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn (name = "sudoku_id")
    private Sudoku sudoku;
    private LocalTime tiempo;
    private Boolean resuelto;
    private Integer Dificultad;

    public Integer getDificultad() {
        return Dificultad;
    }

    public void setDificultad(Integer dificultad) {
        Dificultad = dificultad;
    }

    public Boolean getResuelto() {
        return resuelto;
    }

    public void setResuelto(Boolean resuelto) {
        this.resuelto = resuelto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sudoku getSudoku() {
        return sudoku;
    }

    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    public LocalTime getTiempo() {
        return tiempo;
    }

    public void setTiempo(LocalTime tiempo) {
        this.tiempo = tiempo;
    }

    public long getTiempoEnSegundos(){
        if (tiempo == null) {
            return 0;
        }
        return tiempo.toSecondOfDay();
    }

}
