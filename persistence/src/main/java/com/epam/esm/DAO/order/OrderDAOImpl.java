package com.epam.esm.DAO.order;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;


@Repository
public class OrderDAOImpl implements OrderDAO {

    private final EntityManager entityManager;

    private final static String QUERY_GET_ALL_ORDER = "select o from Order o";
    private final static String GET_ORDERS_BY_USER_ID = "select o from Order o where o.user=:user";

    public OrderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order get(UUID id) {
        try {
            return entityManager.find(Order.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Order> getAll(int limit, int offset) {
        return entityManager
                .createQuery(QUERY_GET_ALL_ORDER, Order.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<Order> getOrdersByUserId(User user, int limit, int offset) {

        return entityManager
                .createQuery(GET_ORDERS_BY_USER_ID, Order.class)
                .setParameter("user", user)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Order getOrderByUserId(UUID userId, UUID orderId) {
        String GET_ORDER_BY_USER_ID = "select * from orders where user_id='" + userId + "' and id='" + orderId + "'";
        return (Order) entityManager
                .createNativeQuery(GET_ORDER_BY_USER_ID, Order.class)
                .getSingleResult();
    }
}
