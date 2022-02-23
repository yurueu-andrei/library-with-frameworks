package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Role;
import by.library.yurueu.entity.User;
import by.library.yurueu.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;

public class RoleRepositoryImpl extends AbstractRepositoryImpl<Role> implements RoleRepository {
    private static final String ROLE_NAME_COLUMN = "roleName";

    private static final String SELECT_ALL_QUERY = "from Role";
    private static final String UPDATE_QUERY = "UPDATE Role SET roleName=:roleName WHERE id=:id";

    public RoleRepositoryImpl() {
        super(Role.class);
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected void constructQuery(Query query, Role role){
        query.setParameter(ROLE_NAME_COLUMN, role.getRoleName())
                .setParameter(ID_COLUMN, role.getId());
    }

    @Override
    protected void deleteLinks(Session session, Role role) {
        deleteUserRoleLinks(role, role.getUsers());
    }

    private void deleteUserRoleLinks(Role role, Set<User> users) {
        users.forEach(user -> user.getRoles().remove(role));
    }
}