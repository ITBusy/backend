package com.poly.dto;

import com.poly.entity.OrderDetails;
import com.poly.entity.Product;
import com.poly.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long oid;
    private String deliveryAddress;
    private String notes;
    private String recipientName;
    private String phoneNumber;
    private Date orderDate;
    private Date deliveryDate;
    private Date recipientDate;
    private String status = "Cart";
    private Double total;
    private User userOrder;
    private Product product;
    private Integer quantity = 1;
    private List<OrderDetails> orderDetailsList;
}
