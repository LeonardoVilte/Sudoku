package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.channels.SeekableByteChannel;
import java.time.LocalTime;
import java.util.List;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
         return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Usuario WHERE email = :email", Usuario.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public Usuario buscarUsuarioPorNombre(String nombre) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Usuario WHERE nombre = :nombre", Usuario.class)
                .setParameter("nombre", nombre)
                .uniqueResult();
    }

    @Override
    public List<Usuario> traerRankingUsuarios() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Usuario u ORDER  BY u.id ASC", Usuario.class)
                .getResultList();
    }

    @Override
    public void actualizarDatosDeLaPartida(Usuario usuario, Long tiempoResuelto) {
        Session session = sessionFactory.getCurrentSession();
        Long idUsuario = usuario.getId();
        Long tiempoResueltoTotal  = usuario.getHorasJugadas();
        usuario.setCantidadPartidasJugadas(usuario.getCantidadPartidasJugadas() + 1);
        Integer partidasTotalesJugadas = usuario.getCantidadPartidasJugadas();

        String hql = "UPDATE Usuario u SET u.horasJugadas = :tiempoResueltoTotal,u.cantidadPartidasJugadas = :partidasTotalesJugadas WHERE u.id = :idUsuario";

        int filasActualizadas = session.createQuery(hql)
                .setParameter("tiempoResueltoTotal", tiempoResueltoTotal)
                .setParameter("idUsuario", idUsuario)
                .setParameter("partidasTotalesJugadas", partidasTotalesJugadas)
                .executeUpdate();
    }

    @Override
    public LocalTime obtenerTiempoJugadoDelUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

        List<Partida> partidas = session.createQuery("FROM Partida p WHERE p.usuario.id = :usuarioId", Partida.class)
                .setParameter("usuarioId", usuario.getId())
                .getResultList();

        Long totalSegundos = partidas.stream()
                .mapToLong(Partida::getTiempoEnSegundos)
                .sum();

        return LocalTime.ofSecondOfDay(totalSegundos);
    }

    @Override
    public Integer obtenerCantidadDePartidasCompletadas(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

        Long cantidadPartidasCompletadas = session.createQuery("SELECT count(p) FROM Partida p WHERE p.usuario.id= :usuarioId AND  p.resuelto = true", Long.class)
                .setParameter("usuarioId" , usuario.getId())
                .uniqueResult();
        return cantidadPartidasCompletadas !=null ? cantidadPartidasCompletadas.intValue() : 0;
    }


    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

}
