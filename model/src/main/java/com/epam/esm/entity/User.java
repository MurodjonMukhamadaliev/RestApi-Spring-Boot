package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Order> orders;

    public User() {
    }

    public User(String username, String phoneNumber, List<Order> orders) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
    }

    public User(UUID id, LocalDateTime createDate, LocalDateTime lastUpdateDate, String username, String phoneNumber, List<Order> orders) {
        super(id, createDate, lastUpdateDate);
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
