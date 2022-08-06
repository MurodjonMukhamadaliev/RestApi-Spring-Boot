package com.epam.esm.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;


public class OrderDTO extends RepresentationModel<OrderDTO> {
    @JsonProperty("gift_certificate")
    private GiftCertificateDTO giftCertificateDTO;
    @JsonProperty("user")
    private UserDTO userDTO;
    private Double cost;
    @JsonProperty("purchase_date")
    private LocalDateTime createDate;

    public OrderDTO() {
    }

    public OrderDTO(GiftCertificateDTO giftCertificateDTO, UserDTO userDTO, Double cost, LocalDateTime createDate) {
        this.giftCertificateDTO = giftCertificateDTO;
        this.userDTO = userDTO;
        this.cost = cost;
        this.createDate = createDate;
    }

    public GiftCertificateDTO getGiftCertificateDTO() {
        return giftCertificateDTO;
    }

    public void setGiftCertificateDTO(GiftCertificateDTO giftCertificateDTO) {
        this.giftCertificateDTO = giftCertificateDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
