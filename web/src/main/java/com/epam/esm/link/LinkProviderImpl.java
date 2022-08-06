package com.epam.esm.link;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.DTO.UserDTO;
import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class LinkProviderImpl implements LinkProvider {

    @Override
    public void addLinkToGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificateController.class).getCertificates("", "", "", 10, 0)).withRel("Get the first 10 Gift Certificates"));
        List<TagDTO> tagDTOList = giftCertificateDTO.getTags();
        tagDTOList.forEach(this::addLinkToTag);
    }

    @Override
    public void addLinkToTag(TagDTO tagDTO) {
        tagDTO.add(linkTo(methodOn(TagController.class).getTags(10, 0)).withRel("Get the first 10 Tags"));
    }

    @Override
    public void addLinkToOrder(OrderDTO orderDTO) {
        orderDTO.add(linkTo(methodOn(OrderController.class).getOrders(10, 0)).withRel("Get the first 10 Orders"));
        UserDTO userDTO = orderDTO.getUserDTO();
        addLinkToUser(userDTO);
        GiftCertificateDTO giftCertificateDTO = orderDTO.getGiftCertificateDTO();
        addLinkToGiftCertificate(giftCertificateDTO);
        List<TagDTO> tags = giftCertificateDTO.getTags();
        tags.forEach(this::addLinkToTag);
    }

    @Override
    public void addLinkToUser(UserDTO userDTO) {
        userDTO.add(linkTo(methodOn(UserController.class).getUsers(10, 0)).withRel("Get the first 10 Users"));
    }
}
