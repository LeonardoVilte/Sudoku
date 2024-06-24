package com.tallerwebi.dominio;

public interface EconomiaService {
    void agregarMonedas(String username, int monedas);
    int obtenerMonedas(String username);
}
