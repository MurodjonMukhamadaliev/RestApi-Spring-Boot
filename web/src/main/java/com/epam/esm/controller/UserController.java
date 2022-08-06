package com.epam.esm.controller;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.UserDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.link.LinkProvider;
import com.epam.esm.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final String LIMIT = "10";
    private final String OFFSET = "0";

    private final UserService userService;
    private final LinkProvider linkProvider;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, LinkProvider linkProvider, ModelMapper modelMapper) {
        this.userService = userService;
        this.linkProvider = linkProvider;
        this.modelMapper = modelMapper;
    }

    /**
     * Get User's Orders
     *
     * @param id     - ID
     * @param limit  - limit
     * @param offset - offset
     * @return List of Order DTO
     * @exception  throws NotFoundException if user not found with the given id
     */

    @GetMapping(value = "/{id}/orders")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable UUID id,
                                                        @RequestParam(required = false, defaultValue = LIMIT) Integer limit,
                                                        @RequestParam(required = false, defaultValue = OFFSET) Integer offset) {
        List<Order> orders = userService.getUserOrders(id, limit, offset);
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

    /**
     * Get User's one  Order with the given id
     *
     * @param userId  - ID of User
     * @param orderId - ID of Order
     * @return Order DTO
     * @exception throws NotFoundException if order or user not found in the DB
     */

    @GetMapping(value = "{userId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> getUserOrder(@PathVariable UUID userId, @PathVariable UUID orderId) {
        Order order = userService.getUserOrder(userId, orderId);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setUserDTO(modelMapper.map(order.getUser(), UserDTO.class));
        orderDTO.setGiftCertificateDTO(modelMapper.map(order.getGiftCertificate(), GiftCertificateDTO.class));
        linkProvider.addLinkToOrder(orderDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderDTO);
    }


    /**
     * Get one user with the given id
     *
     * @param id - ID
     * @return User DTO
     * @exception throws NotFoundException if user not found
     */

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable UUID id) {
        UserDTO user = modelMapper.map(userService.get(id), UserDTO.class);
        linkProvider.addLinkToUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    /**
     * Get Users
     *
     * @param limit  - limit
     * @param offset - offset
     * @return - List of User DTO
     */

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false, defaultValue = LIMIT) Integer limit,
                                                  @RequestParam(required = false, defaultValue = OFFSET) Integer offset) {
        List<User> users = userService.getAll(limit, offset);
        List<UserDTO> userDTOList = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
        userDTOList.forEach(linkProvider::addLinkToUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDTOList);
    }

}
