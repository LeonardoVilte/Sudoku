package com.tallerwebi.presentacion;

public class DatosRegistroDTO {

    private String email;
    private String nombre;
    private String password;
    private String c_password;

    public DatosRegistroDTO(String email, String nombre, String password, String c_password){
        this.email=email;
        this.nombre=nombre;
        this.password=password;
        this.c_password=c_password;
    }
    public DatosRegistroDTO(){}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getC_password() {
        return c_password;
    }

    public void setC_password(String c_password) {
        this.c_password = c_password;
    }
}
