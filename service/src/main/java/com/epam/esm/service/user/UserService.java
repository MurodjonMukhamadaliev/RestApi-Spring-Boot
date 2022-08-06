package com.epam.esm.service.user;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User get(UUID id);

    List<User> getAll(int limit, int offset);

    List<Order> getUserOrders(UUID id, int limit, int offset);

    Order getUserOrder(UUID userId, UUID orderId);

}
