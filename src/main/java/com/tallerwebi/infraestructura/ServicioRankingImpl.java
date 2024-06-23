package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioRankingImpl implements ServicioRanking {

    private RepositorioUsuario repositorioUsuario;
    @Autowired
    public ServicioRankingImpl (RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario= repositorioUsuario;
    }
    @Override
    public void traerTablaUsuarios() {
        this.repositorioUsuario.traerRankingUsuarios();
    }
}
