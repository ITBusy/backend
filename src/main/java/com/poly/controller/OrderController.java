package com.poly.controller;

import com.poly.dto.OrderDto;
import com.poly.dto.ResponseObject;
import com.poly.dto.SendMailOrderDto;
import com.poly.entity.Order;
import com.poly.entity.OrderDetails;
import com.poly.entity.ShipperOrder;
import com.poly.service.impl.OrderDetailServiceImp;
import com.poly.service.impl.OrderServiceImp;
import com.poly.service.impl.SendMailServiceImp;
import com.poly.service.impl.ShipperOrderServiceImp;
import com.poly.utils.Convert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderServiceImp orderServiceImp;

    @Autowired
    private OrderDetailServiceImp orderDetailServiceImp;

    @Autowired
    private ShipperOrderServiceImp shipperOrderServiceImp;

    @Autowired
    private SendMailServiceImp sendMailServiceImp;

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createOrder(@RequestBody OrderDto orderDto) {
        ResponseEntity<ResponseObject> message = null;
        Order order = new Order();
        OrderDetails orderDetails = new OrderDetails();
        try {
            Order orderItem = this.orderServiceImp.findOrderByUserAndStatus(orderDto.getUserOrder(), orderDto.getStatus());
            if (orderItem == null) {
                order.setStatus(orderDto.getStatus());
                order.setUserOrder(orderDto.getUserOrder());
                order.setOrderDate(orderDto.getOrderDate());
                Order order1 = this.orderServiceImp.createOrder(order);

                orderDetails.setOrder(order1);
                orderDetails.setPrice(orderDto.getProduct().getPrice());
                orderDetails.setProduct(orderDto.getProduct());
                orderDetails.setQuantity(orderDto.getQuantity());
                OrderDetails orderDetails1 = this.orderDetailServiceImp.createOrderDetails(orderDetails);

                if (order1 != null && orderDetails1 != null) {
                    message = ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Create order and order detail is success", order));
                }
            } else {
                OrderDetails od;
                OrderDetails orderDetails1 = this.orderDetailServiceImp
                        .checkOrderAndProduct(orderDto.getProduct(), order.getStatus(), orderItem, orderItem.getUserOrder());
                if (orderDetails1 == null) {
                    OrderDetails orderDetails2 = new OrderDetails();
                    orderDetails2.setOrder(orderItem);
                    orderDetails2.setQuantity(orderDto.getQuantity());
                    orderDetails2.setProduct(orderDto.getProduct());
                    orderDetails2.setPrice(orderDto.getProduct().getPrice());
                    od = this.orderDetailServiceImp.createOrderDetails(orderDetails2);
                } else {
                    orderDetails1.setQuantity(orderDetails1.getQuantity() + orderDto.getQuantity());
                    od = this.orderDetailServiceImp.createOrderDetails(orderDetails1);
                }
                if (od != null) {
                    message = ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("OK", "Create order detail is success", od)
                    );
                }
            }
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Create order is failed", null)
            );
        }
        return message;
    }

    @GetMapping("/order-details/{username}")
    public ResponseEntity<ResponseObject> findAllOrderDetails(@PathVariable("username") String username) {
        return ResponseEntity.ok(
                new ResponseObject("Ok", "Data of order details", this.orderDetailServiceImp.findAllOrderDetails(username))
        );
    }

    @PutMapping("/order-detail")
    public ResponseEntity<ResponseObject> updateOrderDetail(@RequestBody OrderDetails orderDetails) {
        OrderDetails orderDetail = this.orderDetailServiceImp.updateOrderDetail(orderDetails);
        if (orderDetail != null) {
            return ResponseEntity.ok(
                    new ResponseObject("Ok", "Update quantity of product " + orderDetail.getProduct().getName() + " is success", orderDetail)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Ok", "Update quantity of product is failed", null)
            );
        }

    }

    @GetMapping("/order-detail/{id}")
    public ResponseEntity<ResponseObject> findOrderDetailById(@PathVariable("id") Long id) {
        OrderDetails orderDetails = this.orderDetailServiceImp.findOrderDetailsById(id);
        if (orderDetails != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Found data of code order detail " + id, orderDetails)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Don't found data of code order detail " + id, null)
            );
        }
    }

    @GetMapping("/total/{id}")
    public Double getTotal(@PathVariable("id") Long id) {
        OrderDetails orderDetails = this.orderDetailServiceImp.findOrderDetailsById(id);
        return this.orderDetailServiceImp.getAmountOfOrder(orderDetails.getOrder().getUserOrder(), orderDetails.getOrder().getStatus());
    }

    @DeleteMapping("/order-detail/{id}")
    public ResponseEntity<ResponseObject> deleteByOrderDetailId(@PathVariable("id") Long id) {
        OrderDetails orderDetail = this.orderDetailServiceImp.findOrderDetailsById(id);
        this.orderDetailServiceImp.deleteOrderDetailsById(orderDetail);
        return ResponseEntity.ok(new ResponseObject("Ok", "Delete of product " + orderDetail.getProduct().getName() + " is success", orderDetail));
    }

    @PutMapping("/")
    public ResponseEntity<ResponseObject> updateOrder(@RequestBody OrderDto orderDto) {
        ResponseEntity<ResponseObject> message = null;
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        order.setRecipientName(Convert.CapitalAllFirstLetter(orderDto.getRecipientName()));
        order.setDeliveryAddress(Convert.CapitalAllFirstLetter(orderDto.getDeliveryAddress()));
        if (order.getNotes() != null) {
            order.setNotes(Convert.CapitalFirstLetter(orderDto.getNotes()));
        }
        Order o1 = this.orderServiceImp.findOrderById(orderDto.getOid());
        o1.setRecipientName(order.getRecipientName());
        o1.setDeliveryAddress(order.getDeliveryAddress());
        o1.setNotes(order.getNotes());
        o1.setTotal(order.getTotal());
        o1.setPhoneNumber(order.getPhoneNumber());
        o1.setStatus(order.getStatus());
        Order order1 = this.orderServiceImp.createOrder(o1);
        try {
            if (order1 != null) {
                message = ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Ok", "Update order is success", order1)
                );
            }
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Ok", "Update order is failed " + e.getMessage(), null)
            );
        }
        return message;
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseObject> findAllOrderByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(new ResponseObject("Ok", "have data", this.orderServiceImp.findAllByUser(username)));
    }

    @GetMapping("/status/{username}/{status}")
    public ResponseEntity<ResponseObject> findAllOrderAndOrderDetailByUsername(@PathVariable("username") String username, @PathVariable("status") String status) {
        ResponseEntity<ResponseObject> message = null;
        List<OrderDto> dtoList = this.orderServiceImp.findAllOrderByUsernameAndStatus(username, status);
        try {
            if (!dtoList.isEmpty()) {
                message = ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "have data", dtoList));
            }
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Not Found", "don't have data " + e.getMessage(), null));
        }
        return message;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> findAllOrder() {
        return ResponseEntity.ok(
                new ResponseObject("Ok", "Have data", this.orderServiceImp.findAllOrder())
        );
    }

    @PutMapping("/update-status")
    public ResponseEntity<ResponseObject> updateStatusAndCreateShipperOrder(@RequestBody ShipperOrder shipperOrder) {
        ResponseEntity<ResponseObject> message = null;
        try {
            if (shipperOrder != null) {
                Order order = this.orderServiceImp.updateOrderByStatus(shipperOrder.getOrder().getOId());
                ShipperOrder shipperOrder1 = this.shipperOrderServiceImp.createShipperOrder(shipperOrder);
                if (order != null && shipperOrder1 != null) {
                    message = ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("Ok", "Update status of order and create shipper order is success", shipperOrder1)
                    );
                }
            }
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Don't can update status of order and create shipper order because it's failed", null)
            );
        }
        return message;
    }

    @GetMapping("/shipper-order-all/{username}")
    public ResponseEntity<ResponseObject> findAllShipperOrder(@PathVariable("username") String username) {
        return ResponseEntity.ok(
                new ResponseObject("Ok", "Have data", this.shipperOrderServiceImp.findAllShipperOrders(username))
        );
    }

    @PutMapping("/update-status/completed")
    public ResponseEntity<ResponseObject> updateOrderByStatusCompleted(@RequestParam("orderID") Long orderID) {
        ResponseEntity<ResponseObject> message = null;
        Order order = this.orderServiceImp.updateOrderByStatusCompleted(orderID);
        try {
            if (order != null) {
                message = ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Ok", "Update status of order is success", order)
                );
            }
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Ok", "Update status of order is failed " + e.getMessage(), null)
            );
        }
        return message;
    }

    @PutMapping("/update-status/cancelled")
    public ResponseEntity<ResponseObject> updateOrderByStatusCancelled(@RequestBody OrderDto orderDto) {
        ResponseEntity<ResponseObject> message = null;
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        order.setOId(orderDto.getOid());
        Order order1 = this.orderServiceImp.updateOrderByStatusCancelled(order);
        try {
            if (order1 != null) {
                message = ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Convert status to cancelled orders is success", order));
            }
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Convert status to cancelled orders is success", order));
        }
        return message;
    }

    @GetMapping("/count-cart/{username}")
    public Long countOrderDetailsByUserAndStatus(@PathVariable("username") String username) {
        return this.orderDetailServiceImp.countOrderDetailsByUserAndStatus(username);
    }

    @GetMapping("/shipper-order/{oid}")
    public ResponseEntity<ResponseObject> findShipperByOrderId(@PathVariable("oid") Long oid) {
        return ResponseEntity.ok(
                new ResponseObject("Ok", "Have data", this.shipperOrderServiceImp.findByOrderID(oid))
        );
    }

    @PostMapping("/send-mail-order")
    public ResponseEntity<ResponseObject> sendMailOrder(@RequestBody SendMailOrderDto sendMailOrderDto) {
        ResponseEntity<ResponseObject> message;
        try {
            this.sendMailServiceImp.sendMailOrder(sendMailOrderDto);
            message = ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Send mail order is success", null)
            );
        } catch (MessagingException e) {
            message = ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Send mail order is failed " + e.getMessage(), null)
            );
        }
        return message;
    }

    @GetMapping("/turnover/{status}/{month}/{year}")
    @Transactional
    public ResponseEntity<ResponseObject> listTurnover(@PathVariable("status") String status,
                                                       @PathVariable("month") Integer month,
                                                       @PathVariable("year") Integer year) {
        return ResponseEntity.ok(
                new ResponseObject("Ok", "Have data", this.orderServiceImp.turnoverDtoList(status, month, year))
        );
    }
}
