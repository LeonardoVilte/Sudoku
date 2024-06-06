package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioJuego;
import com.tallerwebi.dominio.Sudoku;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    @Override
    public Sudoku buscarSudokuNoResuelto(Long usuarioID){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Sudoku s WHERE s.id NOT IN (" +
                "SELECT p.sudoku FROM Partida p WHERE p.usuario = :usuarioId)", Sudoku.class)
                .setParameter("usuarioId", usuarioID)
                .setMaxResults(1)
                .uniqueResult();
    }


}
