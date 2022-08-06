package com.epam.esm.service.order;

import com.epam.esm.DAO.gift_certificate.GiftCertificateDAOImpl;
import com.epam.esm.DAO.order.OrderDAOImpl;
import com.epam.esm.DAO.user.UserDAOImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.epam.esm.service.utils.GiftCertificateUtils.giftCertificate;
import static com.epam.esm.service.utils.OrderUtils.*;
import static com.epam.esm.service.utils.UserUtils.user;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private UserDAOImpl userDAOImpl;

    @Mock
    private GiftCertificateDAOImpl giftCertificateDAOImpl;

    @Mock
    private OrderDAOImpl orderDAOImpl;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    private UUID uuid;
    private Order order;

    @BeforeEach
    void setUp() {
        uuid = uuid();
        order = order();
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenCreatingOrder() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> orderServiceImpl.create(uuid, uuid));
        assertEquals("User not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldThrowGiftCertificateNotFoundExceptionWhenCreatingOrder() {
        when(userDAOImpl.get(uuid)).thenReturn(user());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> orderServiceImpl.create(uuid, uuid));
        assertEquals("Gift Certificate not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenCreatingOrder() {
        when(userDAOImpl.get(uuid)).thenReturn(user());
        when(giftCertificateDAOImpl.get(uuid)).thenReturn(giftCertificate());
        when(orderDAOImpl.create(any())).thenReturn(order);
        Order createdOrder = orderServiceImpl.create(uuid, uuid);
        assertNotNull(createdOrder);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingOrder() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> orderServiceImpl.get(uuid));
        assertEquals("Order not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenGettingGiftCertificate() {
        when(orderDAOImpl.get(uuid)).thenReturn(order);
        Order getOrder = orderServiceImpl.get(uuid);
        assertNotNull(getOrder);
    }
}