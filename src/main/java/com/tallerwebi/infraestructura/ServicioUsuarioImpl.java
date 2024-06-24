package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl  implements ServicioUsuario {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) { this.repositorioUsuario = repositorioUsuario;}

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return repositorioUsuario.buscarPorEmail(email);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public void actualizarMonedas(String email, Integer cantidad) {
        Usuario usuario = repositorioUsuario.buscarPorEmail(email);
        usuario.setMonedas(usuario.getMonedas() + cantidad);
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public void actualizarPistas(String email, Integer cantidad) {
        Usuario usuario = repositorioUsuario.buscarPorEmail(email);
        usuario.setPistas(usuario.getPistas() + cantidad);
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public void actualizarAyudas(String email, Integer cantidad) {
        Usuario usuario = repositorioUsuario.buscarPorEmail(email);
        usuario.setAyudas(usuario.getAyudas() + cantidad);
        repositorioUsuario.modificar(usuario);
    }

}
