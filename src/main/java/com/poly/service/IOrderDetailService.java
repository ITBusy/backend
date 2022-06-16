package com.poly.service;

import com.poly.entity.Order;
import com.poly.entity.OrderDetails;
import com.poly.entity.Product;
import com.poly.entity.User;

import java.util.List;

public interface IOrderDetailService {
    OrderDetails createOrderDetails(OrderDetails orderDetails);

    OrderDetails checkOrderAndProduct(Product product, String status, Order order, User userOrder);

    Long countOrderDetailsByUserAndStatus(String username);

    Long countOrderDetailsByUserAndStatus1(User orderUser, String status);

    List<OrderDetails> findAllOrderDetails(String username);

    Double getAmountOfOrder(User orderUser, String status);

    OrderDetails updateOrderDetail(OrderDetails orderDetails);

    OrderDetails getOrderId(User orderUser, String status);

    OrderDetails findOrderDetailsById(Long id);

    void deleteOrderDetailsById(OrderDetails orderDetails);

    List<OrderDetails> findOrderDetailsByOrderId(Long id);
}
