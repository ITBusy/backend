package com.poly.service;

import com.poly.dto.ShipperOrderDto;
import com.poly.entity.ShipperOrder;

import java.util.List;

public interface IShipperOrderService {
    ShipperOrder createShipperOrder(ShipperOrder shipperOrder);

    List<ShipperOrderDto> findAllShipperOrders(String username);

    ShipperOrder updateShipperOrderByStatusRFGs(ShipperOrder shipperOrder);

    ShipperOrder findByOrderID(Long oId);
}
