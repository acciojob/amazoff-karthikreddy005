package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add-order")
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }

    @PostMapping("/add-partner/{partnerId}")
    public void addPartner(@PathVariable String partnerId) {
        orderService.addPartner(partnerId);
    }

    @PutMapping("/add-order-partner-pair")
    public void addOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId) {
        orderService.addOrderPartnerPair(orderId, partnerId);
    }

    @GetMapping("/get-order-by-id/{orderId}")
    public Order getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public DeliveryPartner getPartnerById(@PathVariable String partnerId) {
        return orderService.getPartnerById(partnerId);
    }

    @GetMapping("/get-all-orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public int getOrderCountByPartnerId(@PathVariable String partnerId) {
        return orderService.getOrderCountByPartnerId(partnerId);
    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public List<Order> getOrdersByPartnerId(@PathVariable String partnerId) {
        return orderService.getOrdersByPartnerId(partnerId);
    }

    @GetMapping("/get-count-of-unassigned-orders")
    public int getCountOfUnassignedOrders() {
        return orderService.getCountOfUnassignedOrders();
    }

    @GetMapping("/get-count-of-undelivered-orders-after-given-time")
    public int getCountOfUndeliveredOrdersAfterGivenTime(@RequestParam String time, @RequestParam String partnerId) {
        return orderService.getCountOfUndeliveredOrdersAfterGivenTime(time, partnerId);
    }

    @GetMapping("/get-last-delivery-time-by-partner-id/{partnerId}")
    public String getLastDeliveryTimeByPartnerId(@PathVariable String partnerId) {
        return orderService.getLastDeliveryTimeByPartnerId(partnerId);
    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public void deletePartnerById(@PathVariable String partnerId) {
        orderService.deletePartnerById(partnerId);
    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public void deleteOrderById(@PathVariable String orderId) {
        orderService.deleteOrderById(orderId);
    }
}
