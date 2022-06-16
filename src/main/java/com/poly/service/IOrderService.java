package com.poly.service;

import com.poly.dto.OrderDto;
import com.poly.entity.Order;
import com.poly.entity.User;

import java.util.List;

public interface IOrderService {
    Order findOrderByUserAndStatus(User user, String status);

    Order createOrder(Order order);

    Order findOrderById(Long oid);

    List<OrderDto> findAllByUser(String username);

    //    Con loi chua sua
    List<OrderDto> findAllOrderByUsernameAndStatus(String username, String status);

    List<OrderDto> findAllOrder();

    Order updateOrderByStatus(Long orderId);

    Order updateOrderByStatusRFGs(Long orderId);

    Order updateOrderByStatusCompleted(Long orderId);

    Order updateOrderByStatusCancelled(Order order);
}
