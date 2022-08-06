package com.epam.esm.DAO.user;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;


@Repository
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    private final static String QUERY_GET_ALL_USER = "select u from User u";

    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User get(UUID id) {
        try {
            return entityManager.find(User.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<User> getAll(int limit, int offset) {
        return entityManager
                .createQuery(QUERY_GET_ALL_USER, User.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }
}
