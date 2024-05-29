package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ContrasenasDistintas;
import com.tallerwebi.dominio.excepcion.NombreDeUsuarioRepetido;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.DatosRegistroDTO;


public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(DatosRegistroDTO usuario) throws UsuarioExistente, ContrasenasDistintas, NombreDeUsuarioRepetido;

}
