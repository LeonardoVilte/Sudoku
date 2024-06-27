package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.RepositorioJuego;
import com.tallerwebi.dominio.Sudoku;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository("repositorioJuego")
public class RepositorioJuegoImpl implements RepositorioJuego {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioJuegoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarSudoku(Sudoku sudoku) {
        sessionFactory.getCurrentSession().save(sudoku);
    }

    //Esto metodo se deberia utilizar si los sudokus que se entregan al usuario son precargados.
    @Override
    public Sudoku buscarSudokuNoResuelto(Long usuarioID){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Sudoku s WHERE s.id NOT IN (" +
                "SELECT p.sudoku FROM Partida p WHERE p.usuario = :usuarioId)", Sudoku.class)
                .setParameter("usuarioId", usuarioID)
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public void guardarPartida(Partida partida) {
        sessionFactory.getCurrentSession().save(partida);
    }

    @Override
    public Partida buscarPartidaPorId(Long idPartida){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Partida p WHERE p.id = :idPartida", Partida.class)
                .setParameter("idPartida", idPartida)
                .uniqueResult();
    }

    @Override
    public List<Partida> traerPartidasPorDificultad(int dificultad) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Partida p WHERE p.Dificultad = :dificultad ORDER BY p.tiempo ASC", Partida.class)
                .setParameter("dificultad", dificultad)
                .setMaxResults(3)
                .getResultList();
    }

    @Override
    public void guardarDatosEnPartida(Long idPartidaActual, LocalTime tiempoResuelto, boolean resuelto) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "UPDATE Partida p SET p.tiempo = :tiempoResuelto, p.resuelto = :resuelto WHERE p.id = :idPartidaActual";

        int filasActualizadas = session.createQuery(hql)
                .setParameter("tiempoResuelto", tiempoResuelto)
                .setParameter("resuelto", resuelto)
                .setParameter("idPartidaActual", idPartidaActual)
                .executeUpdate();
    }


}
