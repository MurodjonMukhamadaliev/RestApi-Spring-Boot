package com.epam.esm.service.utils;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.entity.Order;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.epam.esm.service.utils.GiftCertificateUtils.giftCertificate;
import static com.epam.esm.service.utils.GiftCertificateUtils.giftCertificateDTO;
import static com.epam.esm.service.utils.UserUtils.user;
import static com.epam.esm.service.utils.UserUtils.userDTO;

public class OrderUtils {

    public static UUID uuid() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public static Order order() {
        return new Order(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                1.1,
                user(),
                giftCertificate()
        );
    }

    public static OrderDTO orderDTO() {
        return new OrderDTO(
                giftCertificateDTO(),
                userDTO(),
                1.1,
                LocalDateTime.now()
        );
    }

}
