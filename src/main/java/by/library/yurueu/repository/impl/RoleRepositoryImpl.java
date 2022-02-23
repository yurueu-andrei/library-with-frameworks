package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Role;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.RoleRepository;
import by.library.yurueu.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public class RoleRepositoryImpl implements RoleRepository {
    private static final String ID_COLUMN = "id";
    private static final String ROLE_NAME_COLUMN = "role_name";

    private static final String SELECT_ALL_QUERY = "from Role";
    private static final String UPDATE_QUERY = "UPDATE Role SET role_name=:role_name WHERE id=:id";

    public Role findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Role.class, id);
        }
    }


    public List<Role> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Role.class).list();
        }
    }

    public Role add(Role role) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(role);
            return role;
        }
    }

    public boolean update(Role role) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                session.createQuery(UPDATE_QUERY)
                        .setParameter(ROLE_NAME_COLUMN, role.getRoleName())
                        .setParameter(ID_COLUMN, role.getId())
                        .executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Role role = session.get(Role.class, id);
                deleteLinks(role);
                session.delete(role);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    private void deleteLinks(Role role) {
        deleteUserRoleLinks(role, role.getUsers());
    }

    private void deleteUserRoleLinks(Role role, Set<User> users) {
        for (User user : users) {
            user.getRoles().remove(role);
        }
    }
}