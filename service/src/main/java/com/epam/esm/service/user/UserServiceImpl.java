package com.epam.esm.service.user;

import com.epam.esm.DAO.order.OrderDAO;
import com.epam.esm.DAO.order.OrderDAOImpl;
import com.epam.esm.DAO.user.UserDAOImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    private final UserDAOImpl userDAOImpl;
    private final OrderDAO orderDAO;

    public UserServiceImpl(UserDAOImpl userDAOImpl, OrderDAOImpl orderDAO) {
        this.userDAOImpl = userDAOImpl;
        this.orderDAO = orderDAO;
    }

    @Override
    public User get(UUID id) {
        User user = userDAOImpl.get(id);
        if (user == null) {
            throw new NotFoundException("User not found id = " + id);
        }
        return user;
    }

    @Override
    public List<User> getAll(int limit, int offset) {
        return userDAOImpl.getAll(limit, offset);
    }

    @Override
    public List<Order> getUserOrders(UUID id, int limit, int offset) {
        User user = userDAOImpl.get(id);
        if(user == null) {
            throw new NotFoundException("User not found id = " + id);
        }
        return orderDAO.getOrdersByUserId(user, limit, offset);
    }

    @Override
    public Order getUserOrder(UUID userId, UUID orderId) {
        if(userDAOImpl.get(userId) == null) {
            throw new NotFoundException("User not found id = " + userId);
        }
        if(orderDAO.get(orderId) == null) {
            throw new NotFoundException("Order not found id = " + orderId);
        }
        return orderDAO.getOrderByUserId(userId, orderId);
    }
}
