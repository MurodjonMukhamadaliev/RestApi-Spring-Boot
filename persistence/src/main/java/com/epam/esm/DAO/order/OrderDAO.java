package com.epam.esm.DAO.order;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.UUID;

public interface OrderDAO {

    Order create(Order order);

    Order get(UUID id);

    List<Order> getAll(int limit, int offset);

    List<Order> getOrdersByUserId(User user, int limit, int offset);

    Order getOrderByUserId(UUID userId, UUID orderId);

}
