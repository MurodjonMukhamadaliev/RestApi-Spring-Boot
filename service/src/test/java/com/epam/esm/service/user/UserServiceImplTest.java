package com.epam.esm.service.user;

import com.epam.esm.DAO.order.OrderDAOImpl;
import com.epam.esm.DAO.user.UserDAOImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.epam.esm.service.utils.OrderUtils.order;
import static com.epam.esm.service.utils.UserUtils.user;
import static com.epam.esm.service.utils.UserUtils.uuid;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDAOImpl userDAOImpl;

    @Mock
    private OrderDAOImpl orderDAOImpl;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UUID uuid;
    private User user;
    private Order order;

    @BeforeEach
    void setUp() {
        uuid = uuid();
        user = user();
        order = order();
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingGiftCertificate() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userServiceImpl.get(uuid));
        assertEquals("User not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenGettingGiftCertificate() {
        when(userDAOImpl.get(uuid)).thenReturn(user);
        User getUser = userServiceImpl.get(uuid);
        assertNotNull(getUser);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingUserOrders() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userServiceImpl.getUserOrders(uuid, 10, 0));
        assertEquals("User not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenGettingUserOrders() {
        when(userDAOImpl.get(uuid)).thenReturn(user);
        when(orderDAOImpl.getOrdersByUserId(user, 10, 0)).thenReturn(List.of(order));
        List<Order> userOrders = userServiceImpl.getUserOrders(uuid, 10, 0);
        assertEquals(1, userOrders.size());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenGettingUserOrder() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userServiceImpl.getUserOrder(uuid, uuid));
        assertEquals("User not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldThrowOrderNotFoundExceptionWhenGettingUserOrder() {
        when(userDAOImpl.get(uuid)).thenReturn(user);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userServiceImpl.getUserOrder(uuid, uuid));
        assertEquals("Order not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenGettingUserOrder() {
        when(userDAOImpl.get(uuid)).thenReturn(user);
        when(orderDAOImpl.get(uuid)).thenReturn(order);
        when(orderDAOImpl.getOrderByUserId(uuid, uuid)).thenReturn(order);
        Order userOrder = userServiceImpl.getUserOrder(uuid, uuid);
        assertNotNull(userOrder);
    }
}