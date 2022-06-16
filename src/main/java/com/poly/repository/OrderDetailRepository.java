package com.poly.repository;

import com.poly.entity.Order;
import com.poly.entity.OrderDetails;
import com.poly.entity.Product;
import com.poly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long> {
    Optional<OrderDetails> findByProductAndOrder_StatusAndOrderAndOrder_UserOrder(Product product, String status, Order order, User userOrder);

    long countByOrder_UserOrder_UsernameAndOrder_Status(String username, String status);

    Optional<Collection<OrderDetails>> findByOrder_UserOrderAndOrder_Status(User userOrder, String status);

    Optional<List<OrderDetails>> findByOrder_oId(Long oId);


}
