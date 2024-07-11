package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;
    private String nombre;
    @OneToMany(mappedBy = "usuario" , cascade = CascadeType.ALL)
    private List<Partida> partidas;
    private Long horasJugadas = 0L; // Inicializamos en cero
    private Integer cantidadPartidasJugadas = 0;
    private Double tiempoPromedioResolucion = 0.0;
    private Integer monedas = 0;
    private Integer pistasDisponibles = 3;
    private Integer ayudasDisponibles  = 5;

    public Integer getPistasDisponibles() {
        return pistasDisponibles;
    }

    public void setPistasDisponibles(Integer pistasDisponibles) {
        this.pistasDisponibles = pistasDisponibles;
    }

    public Integer getAyudasDisponibles() {
        return ayudasDisponibles;
    }

    public void setAyudasDisponibles(Integer ayudasDisponibles) {
        this.ayudasDisponibles = ayudasDisponibles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(Long horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public Integer getCantidadPartidasJugadas() {
        return cantidadPartidasJugadas;
    }

    public void setCantidadPartidasJugadas(Integer cantidadPartidasJugadas) {
        this.cantidadPartidasJugadas = cantidadPartidasJugadas;
    }

    public Double getTiempoPromedioResolucion() {
        return tiempoPromedioResolucion;
    }

    public void setTiempoPromedioResolucion(Double tiempoPromedioResolucion) {
        this.tiempoPromedioResolucion = tiempoPromedioResolucion;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }
//
}
