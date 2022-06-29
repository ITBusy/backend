package com.poly.service.impl;

import com.poly.entity.Order;
import com.poly.entity.OrderDetails;
import com.poly.entity.Product;
import com.poly.entity.User;
import com.poly.repository.OrderDetailRepository;
import com.poly.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImp implements com.poly.service.IOrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        return this.orderDetailRepository.save(orderDetails);
    }

    @Override
    public OrderDetails checkOrderAndProduct(Product product, String status, Order order, User userOrder) {
        return this.orderDetailRepository
                .findByProductAndOrder_StatusAndOrderAndOrder_UserOrder(product, status, order, userOrder).orElse(null);
    }

    @Override
    public Long countOrderDetailsByUserAndStatus(String username) {
        return this.orderDetailRepository.countByOrder_UserOrder_UsernameAndOrder_Status(username, "Cart");
    }

    @Override
    public Long countOrderDetailsByUserAndStatus1(User orderUser, String status) {
        return this.orderDetailRepository
                .findByOrder_UserOrderAndOrder_Status(orderUser, status).stream().count();
    }

    @Override
    public List<OrderDetails> findAllOrderDetails(String username) {
        return this.orderDetailRepository.
                findAll()
                .stream()
                .filter(e -> Objects.equals(e.getOrder().getStatus(), "Cart")
                        && Objects.equals(e.getOrder().getUserOrder().getUsername(), username)
                )
                .collect(Collectors.toList());
    }

    @Override
    public Double getAmountOfOrder(User orderUser, String status) {
        return this.orderDetailRepository
                .findByOrder_UserOrderAndOrder_Status(orderUser, status)
                .map(orderDetails -> orderDetails.stream().mapToDouble(e -> e.getPrice() * e.getQuantity()).sum())
                .orElse(0.0);
//        BigDecimal sum = Objects.requireNonNull(this.orderDetailRepository
//                        .findByOrder_UserOrderAndOrder_Status(orderUser, status).orElse(null))
//                .stream().map(data -> BigDecimal.valueOf(data.getQuantity()).multiply(BigDecimal.valueOf(data.getPrice())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        return sum.doubleValue();
    }

    @Override
    public OrderDetails updateOrderDetail(OrderDetails orderDetails) {
        OrderDetails orderDetail = this.findOrderDetailsById(orderDetails.getOrderDetailsId());
        Long orderId = orderDetail.getOrder().getOId();
        if (orderDetail.getOrder().getStatus().equals("Cart")) {
            if (orderDetails.getQuantity() <= 0) {
                this.orderDetailRepository.delete(orderDetail);
                List<OrderDetails> orderDetails1 = this.orderDetailRepository.findByOrder_oId(orderId).orElse(null);
                if (orderDetails1.isEmpty()) {
                    this.orderRepository.deleteById(orderId);
                }
            } else {
                orderDetail.setQuantity(orderDetails.getQuantity());
                this.orderDetailRepository.save(orderDetail);
            }
        }
        return orderDetail;
    }

    @Override
    public OrderDetails getOrderId(User orderUser, String status) {
        Collection<OrderDetails> orderDetails = this.orderDetailRepository.findByOrder_UserOrderAndOrder_Status(orderUser, status).orElse(null);
        assert orderDetails != null;
        return orderDetails.stream().findFirst().orElse(null);
    }

    @Override
    public OrderDetails findOrderDetailsById(Long id) {
        return this.orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOrderDetailsById(OrderDetails orderDetails) {
        Order order = orderDetails.getOrder();
        this.orderDetailRepository.delete(orderDetails);
        this.orderRepository.delete(order);
    }

    @Override
    public List<OrderDetails> findOrderDetailsByOrderId(Long id) {
        return this.orderDetailRepository.findByOrder_oId(id).orElse(null);
    }
}
