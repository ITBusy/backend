package com.poly.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ShipperOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shipperOrderId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applicationTime;
    private Double total;
    private String reasonRTGs;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipper_user")
    private User shipperUser;

    public ShipperOrder() {
    }

    public ShipperOrder(Long shipperOrderId, Order order, Date applicationTime, Double total, String reasonRTGs, User shipperUser) {
        this.shipperOrderId = shipperOrderId;
        this.order = order;
        this.applicationTime = applicationTime;
        this.total = total;
        this.reasonRTGs = reasonRTGs;
        this.shipperUser = shipperUser;
    }

    public Long getShipperOrderId() {
        return shipperOrderId;
    }

    public void setShipperOrderId(Long shipperOrderId) {
        this.shipperOrderId = shipperOrderId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getShipperUser() {
        return shipperUser;
    }

    public void setShipperUser(User shipperUser) {
        this.shipperUser = shipperUser;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getReasonRTGs() {
        return reasonRTGs;
    }

    public void setReasonRTGs(String reasonRTGs) {
        this.reasonRTGs = reasonRTGs;
    }
}
