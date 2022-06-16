package com.poly.service.impl;

import com.poly.dto.ShipperOrderDto;
import com.poly.entity.OrderDetails;
import com.poly.entity.ShipperOrder;
import com.poly.repository.ShipperOrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipperOrderServiceImp implements com.poly.service.IShipperOrderService {

    @Autowired
    private ShipperOrderRepository shipperOrderRepository;

    @Autowired
    private OrderDetailServiceImp orderDetailServiceImp;

    @Override
    public ShipperOrder createShipperOrder(ShipperOrder shipperOrder) {
        return this.shipperOrderRepository.save(shipperOrder);
    }

    @Override
    public List<ShipperOrderDto> findAllShipperOrders(String username) {
        List<ShipperOrder> list = this.shipperOrderRepository.findByShipperUser_Username(username);
        List<ShipperOrderDto> shipperOrderDtoList = new ArrayList<>();
        for (ShipperOrder e : list) {
            ShipperOrderDto shipperOrderDto = new ShipperOrderDto();
            BeanUtils.copyProperties(e, shipperOrderDto);
            List<OrderDetails> list1 = this.orderDetailServiceImp.findOrderDetailsByOrderId(shipperOrderDto.getOrder().getOId());
            shipperOrderDto.setOrderDetailsList(list1);
            shipperOrderDtoList.add(shipperOrderDto);
        }
        return shipperOrderDtoList;
    }

    @Override
    public ShipperOrder updateShipperOrderByStatusRFGs(ShipperOrder shipperOrder) {
        return this.shipperOrderRepository.save(shipperOrder);
    }

    @Override
    public ShipperOrder findByOrderID(Long oId) {
        return this.shipperOrderRepository.findByOrder_oId(oId).orElse(null);
    }
}
