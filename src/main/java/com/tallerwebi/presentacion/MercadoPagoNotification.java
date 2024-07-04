package com.tallerwebi.presentacion;

public class MercadoPagoNotification {
    private String status;
    private String email;
    private int cantidadMonedas;

    public int getCantidadMonedas() {
        return cantidadMonedas;
    }

    public void setCantidadMonedas(int cantidadMonedas) {
        this.cantidadMonedas = cantidadMonedas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
