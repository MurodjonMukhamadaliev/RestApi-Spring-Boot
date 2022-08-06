package com.epam.esm.link;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.DTO.UserDTO;

public interface LinkProvider {

    void addLinkToGiftCertificate(GiftCertificateDTO giftCertificateDTO);

    void addLinkToTag(TagDTO tagDTO);

    void addLinkToOrder(OrderDTO orderDTO);

    void addLinkToUser(UserDTO userDTO);

}
