package com.poly.dto;

import com.poly.entity.Order;
import com.poly.entity.OrderDetails;
import com.poly.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipperOrderDto {
    private Long shipperOrderId;
    private Order order;
    private Date applicationTime;
    private Double total;
    private String reasonRTGs;
    private User shipperUser;
    private List<OrderDetails> orderDetailsList;
}
