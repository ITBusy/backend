package com.poly.repository;

import com.poly.entity.ShipperOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipperOrderRepository extends JpaRepository<ShipperOrder, Long> {
    List<ShipperOrder> findByShipperUser_Username(String username);

    Optional<ShipperOrder> findByOrder_oId(Long oId);
}
