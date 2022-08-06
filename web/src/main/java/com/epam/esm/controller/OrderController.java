package com.epam.esm.controller;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.UserDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.link.LinkProvider;
import com.epam.esm.service.order.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final String LIMIT = "10";
    private final String OFFSET = "0";

    private final OrderService orderService;
    private final LinkProvider linkProvider;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, LinkProvider linkProvider, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.linkProvider = linkProvider;
        this.modelMapper = modelMapper;
    }

    /**
     * Create Order based on the user and giftCertificate
     *
     * @param userId            - User ID
     * @param giftCertificateId - Gift Certificate ID
     * @return Order DTO
     * @exception throws NotFoundException if user or certificate not found with the given id
     */

    @GetMapping
    public ResponseEntity<OrderDTO> create(@RequestParam UUID userId, @RequestParam UUID giftCertificateId) {
        Order order = orderService.create(userId, giftCertificateId);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setUserDTO(modelMapper.map(order.getUser(), UserDTO.class));
        orderDTO.setGiftCertificateDTO(modelMapper.map(order.getGiftCertificate(), GiftCertificateDTO.class));
        linkProvider.addLinkToOrder(orderDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderDTO);
    }

    /**
     * Get one  Order with the given id
     *
     * @param id - ID
     * @return Order DTO
     * @exception throws NotFoundException if order not found with the given id
     */

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable UUID id) {
        Order order = orderService.get(id);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setUserDTO(modelMapper.map(order.getUser(), UserDTO.class));
        orderDTO.setGiftCertificateDTO(modelMapper.map(order.getGiftCertificate(), GiftCertificateDTO.class));
        linkProvider.addLinkToOrder(orderDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderDTO);
    }

    /**
     * Get Orders with the given requests
     *
     * @param limit  - limit
     * @param offset - offset
     * @return - List of Order DTO
     */

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDTO>> getOrders(@RequestParam(required = false, defaultValue = LIMIT) Integer limit,
                                                    @RequestParam(required = false, defaultValue = OFFSET) Integer offset) {
        List<Order> orders = orderService.getAll(limit, offset);
        List<OrderDTO> orderDTOList = orders.stream().map(order -> {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTO.setUserDTO(modelMapper.map(order.getUser(), UserDTO.class));
            orderDTO.setGiftCertificateDTO(modelMapper.map(order.getGiftCertificate(), GiftCertificateDTO.class));
            return orderDTO;
        }).collect(Collectors.toList());
        orderDTOList.forEach(linkProvider::addLinkToOrder);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderDTOList);
    }

}
