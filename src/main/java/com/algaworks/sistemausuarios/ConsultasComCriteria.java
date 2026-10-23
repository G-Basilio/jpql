package com.algaworks.sistemausuarios;

import com.algaworks.sistemausuarios.dto.UsuarioDTO;
import com.algaworks.sistemausuarios.model.Dominio;
import com.algaworks.sistemausuarios.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.security.PublicKey;
import java.util.List;

public class ConsultasComCriteria {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Usuarios-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //primeirasConsultas(entityManager);
        //escolhendoORetorno(entityManager);
        //fazendoProjecoes(entityManager);
        //passandoParametros(entityManager);
        //ordenandoResultados(entityManager);
        paginandoResultados(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void primeirasConsultas(EntityManager entityManager) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);

        entityManager.createQuery(criteriaQuery).getResultList().forEach(u -> System.out.println(u.getNome()));
    }

    public static void escolhendoORetorno(EntityManager entityManager) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        /*CriteriaQuery<Dominio> criteriaQuery = builder.createQuery(Dominio.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root.get("dominio"));

        entityManager.createQuery(criteriaQuery)
                .getResultList()
                .forEach(d -> System.out.println(d.getNome()));*/

        CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root.get("nome"));

        entityManager.createQuery(criteriaQuery).getResultList().forEach(nome -> System.out.println("Nome: " + nome));
    }

    public static void fazendoProjecoes(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        /*CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.multiselect(root.get("id"), root.get("login"), root.get("nome"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();
        lista.forEach(arr -> System.out.println(String.format("%s, %s, %s", arr)));*/

        CriteriaQuery<UsuarioDTO> criteriaQuery = criteriaBuilder.createQuery(UsuarioDTO.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(criteriaBuilder.construct(UsuarioDTO.class, root.get("id"), root.get("login"), root.get("nome")));

        TypedQuery<UsuarioDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<UsuarioDTO> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println("DTO: " + u.getId() + u.getNome()));
    }

    public static void passandoParametros(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        /*CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
        Usuario usuario = typedQuery.getSingleResult();
        System.out.println(usuario.getId() + ", " + usuario.getNome());*/

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("login"), "torres"));

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
        Usuario usuario = typedQuery.getSingleResult();
        System.out.println(usuario.getId() + ", " + usuario.getNome());

    }

    public static void ordenandoResultados(EntityManager entityManager) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("nome")));

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));

    }

    public static void paginandoResultados(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("nome")));

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(2);

        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));

    }
}
