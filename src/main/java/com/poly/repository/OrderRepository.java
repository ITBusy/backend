package com.poly.repository;

import com.poly.entity.Order;
import com.poly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserOrderAndStatus(User userOrder, String status);

    Optional<List<Order>> findByUserOrder_UsernameAndStatus(String username, String status);

    Optional<List<Order>> findByUserOrder_UsernameAndStatusIsNotIn(String username, List<String> statuses);

    Optional<Order> findByUserOrder_UsernameAndStatusIsNotLikeAndStatusIsNotLikeAndStatusIsNotLikeAndStatusIsNotLike(@NotBlank String userOrder_username, String status, String status2, String status3, String status4);

    @Procedure(procedureName = "turnover")
    List<Object[]> listTurnover(String status, Integer moth, Integer year);
}
