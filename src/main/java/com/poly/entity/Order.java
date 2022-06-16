package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long oId;
    private String deliveryAddress;
    private String notes;
    private String recipientName;
    private String phoneNumber;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recipientDate;
    private String status = "Cart";
    private Double total;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userOrder;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<ShipperOrder> shipperOrder = new LinkedHashSet<>();
    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<OrderDetails> orderDetailsSet = new LinkedHashSet<>();

    public Order() {
    }

    public Order(Long oId, String deliveryAddress, String notes, String recipientName, String phoneNumber, Date orderDate,
                 Date deliveryDate, Date recipientDate, String status, Double total, User userOrder,
                 Set<ShipperOrder> shipperOrder, Set<OrderDetails> orderDetailsSet) {
        this.oId = oId;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.recipientDate = recipientDate;
        this.status = status;
        this.total = total;
        this.userOrder = userOrder;
        this.shipperOrder = shipperOrder;
        this.orderDetailsSet = orderDetailsSet;
    }

    public Long getOId() {
        return oId;
    }

    public void setOId(Long oId) {
        this.oId = oId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getRecipientDate() {
        return recipientDate;
    }

    public void setRecipientDate(Date recipientDate) {
        this.recipientDate = recipientDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<ShipperOrder> getShipperOrder() {
        return shipperOrder;
    }

    public void setShipperOrder(Set<ShipperOrder> shipperOrder) {
        this.shipperOrder = shipperOrder;
    }

    public User getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(User userOrder) {
        this.userOrder = userOrder;
    }

    public Set<OrderDetails> getOrderDetailsSet() {
        return orderDetailsSet;
    }

    public void setOrderDetailsSet(Set<OrderDetails> orderDetailsSet) {
        this.orderDetailsSet = orderDetailsSet;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
