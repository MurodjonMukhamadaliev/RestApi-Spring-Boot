package com.epam.esm.service.order;

import com.epam.esm.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order create(UUID userId, UUID giftCertificateId);

    Order get(UUID id);

    List<Order> getAll(int limit, int offset);

}
