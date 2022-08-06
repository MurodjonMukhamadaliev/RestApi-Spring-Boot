package com.epam.esm.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private BigDecimal cost;

    @ManyToOne
    private User user;

    @ManyToOne
    private GiftCertificate giftCertificate;

    public Order() {
    }

    public Order(BigDecimal cost, User user, GiftCertificate giftCertificate) {
        this.cost = cost;
        this.user = user;
        this.giftCertificate = giftCertificate;
    }

    public Order(UUID id, LocalDateTime createDate, LocalDateTime lastUpdateDate, BigDecimal cost, User user, GiftCertificate giftCertificate) {
        super(id, createDate, lastUpdateDate);
        this.cost = cost;
        this.user = user;
        this.giftCertificate = giftCertificate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }
}
