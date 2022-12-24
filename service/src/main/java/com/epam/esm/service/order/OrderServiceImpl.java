package com.epam.esm.service.order;

import com.epam.esm.DAO.gift_certificate.GiftCertificateDAO;
import com.epam.esm.DAO.gift_certificate.GiftCertificateDAOImpl;
import com.epam.esm.DAO.order.OrderDAOImpl;
import com.epam.esm.DAO.user.UserDAOImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;



@Service
public class OrderServiceImpl implements OrderService {

    private final UserDAOImpl userDAOImpl;
    private final GiftCertificateDAO giftCertificateDAO;
    private final OrderDAOImpl orderDAOImpl;

    public OrderServiceImpl(UserDAOImpl userDAOImpl, GiftCertificateDAOImpl giftCertificateDAO, OrderDAOImpl orderDAOImpl) {
        this.userDAOImpl = userDAOImpl;
        this.giftCertificateDAO = giftCertificateDAO;
        this.orderDAOImpl = orderDAOImpl;

    }

    @Override
    @Transactional
    public Order create(UUID userId, UUID giftCertificateId) {
        User user = userDAOImpl.get(userId);
        if(user == null) {
            throw new NotFoundException("User not found id = " + userId);
        }
        GiftCertificate giftCertificate = giftCertificateDAO.get(giftCertificateId);
        if(giftCertificate == null) {
            throw new NotFoundException("Gift Certificate not found id = " + giftCertificateId);
        }
        return orderDAOImpl.create(new Order(giftCertificate.getPrice(), user, giftCertificate));
    }

    @Override
    public Order get(UUID id) {
        Order order = orderDAOImpl.get(id);
        if (order == null) {
            throw new NotFoundException("Order not found id = " + id);
        }
        return order;
    }

    @Override
    public List<Order> getAll(int limit, int offset) {
        return orderDAOImpl.getAll(limit, offset);
    }
}
