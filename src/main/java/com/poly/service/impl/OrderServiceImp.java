package com.poly.service.impl;

import com.poly.dto.OrderDto;
import com.poly.entity.Order;
import com.poly.entity.OrderDetails;
import com.poly.entity.User;
import com.poly.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImp implements com.poly.service.IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailServiceImp orderDetailServiceImp;

    @Override
    public Order findOrderByUserAndStatus(User user, String status) {
        return this.orderRepository.findByUserOrderAndStatus(user, status).orElse(null);
    }

    @Override
    public Order createOrder(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public Order findOrderById(Long oid) {
        return this.orderRepository.findById(oid).orElse(null);
    }

    @Override
    public List<OrderDto> findAllByUser(String username) {
        List<OrderDto> dtoList = new ArrayList<>();
        List<String> list = new ArrayList<>(List.of(new String[]{"Cart", "Completed", "Returned the goods", "Cancelled orders"}));
        List<Order> orderList = this.orderRepository.findByUserOrder_UsernameAndStatusIsNotIn(username, list).orElse(null);
        assert orderList != null;
        for (Order order : orderList) {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            List<OrderDetails> orderDetail = this.orderDetailServiceImp.findOrderDetailsByOrderId(order.getOId());
            orderDto.setOrderDetailsList(orderDetail);
            orderDto.setOid(order.getOId());
            dtoList.add(orderDto);
        }
        return dtoList;
    }


    //    Con loi chua sua
    @Override
    public List<OrderDto> findAllOrderByUsernameAndStatus(String username, String status) {
        List<Order> orderList = this.orderRepository.findByUserOrder_UsernameAndStatus(username, status).orElse(null);
        List<OrderDto> dtoList = new ArrayList<>();
        assert orderList != null;
        for (Order o : orderList) {
            OrderDto orderDto = new OrderDto();
            List<OrderDetails> orderDetail = this.orderDetailServiceImp.findOrderDetailsByOrderId(o.getOId());
            BeanUtils.copyProperties(o, orderDto);
            orderDto.setOrderDetailsList(orderDetail);
            orderDto.setOid(o.getOId());
            dtoList.add(orderDto);
        }
        return dtoList;
    }

    @Override
    public List<OrderDto> findAllOrder() {
        List<Order> orderList = this.orderRepository.findAll();
        List<OrderDto> orderDetailDto = new ArrayList<>();
        for (Order o : orderList) {
            OrderDto orderDto1 = new OrderDto();
            List<OrderDetails> orderDetail = this.orderDetailServiceImp.findOrderDetailsByOrderId(o.getOId());
            BeanUtils.copyProperties(o, orderDto1);
            orderDto1.setOid(o.getOId());
            orderDto1.setOrderDetailsList(orderDetail);
            orderDetailDto.add(orderDto1);
        }
        return orderDetailDto;
    }

    @Override
    public Order updateOrderByStatus(Long orderId) {
        Order order = this.orderRepository.findById(orderId).orElse(null);
        assert order != null;
        order.setStatus("Delivering");
        order.setDeliveryDate(new Date());
        return this.orderRepository.save(order);
    }

    @Override
    public Order updateOrderByStatusRFGs(Long orderId) {
        Order order = this.orderRepository.findById(orderId).orElse(null);
        assert order != null;
        order.setStatus("Returned the goods");
        order.setRecipientDate(new Date());
        return this.orderRepository.save(order);
    }

    @Override
    public Order updateOrderByStatusCompleted(Long orderId) {
        Order order = this.orderRepository.findById(orderId).orElse(null);
        assert order != null;
        order.setStatus("Completed");
        order.setRecipientDate(new Date());
        return this.orderRepository.save(order);
    }

    @Override
    public Order updateOrderByStatusCancelled(Order order) {
        return this.orderRepository.save(order);
    }
}
