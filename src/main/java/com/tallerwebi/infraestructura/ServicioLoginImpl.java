package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContrasenasDistintas;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.DatosRegistroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email, String password){
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(DatosRegistroDTO datosRegistro) throws UsuarioExistente, ContrasenasDistintas {
        Usuario usuarioEncontrado = repositorioUsuario.buscarPorEmail(datosRegistro.getEmail());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        if(!datosRegistro.getPassword().equals(datosRegistro.getC_password())){
            throw new ContrasenasDistintas();
        }
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setEmail(datosRegistro.getEmail());
        usuarioNuevo.setNombre(datosRegistro.getNombre());
        usuarioNuevo.setPassword(datosRegistro.getPassword());

        repositorioUsuario.guardar(usuarioNuevo);
    }

}

