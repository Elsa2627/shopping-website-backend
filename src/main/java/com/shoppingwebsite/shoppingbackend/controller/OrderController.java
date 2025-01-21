package com.shoppingwebsite.shoppingbackend.controller;

import com.shoppingwebsite.shoppingbackend.model.AppUser;
import com.shoppingwebsite.shoppingbackend.model.CustomerOrder;
import com.shoppingwebsite.shoppingbackend.model.OrderItem;
import com.shoppingwebsite.shoppingbackend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Tag(name = "Orders", description = "Operations related to customer orders") // Annotation Swagger
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerOrder> updateOrder(@PathVariable Long id, @RequestBody CustomerOrder order) {
        CustomerOrder updatedOrder = orderService.updateOrder(id, order);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public CustomerOrder getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }


    @Operation(summary = "Get all orders", description = "Retrieve a list of all customer orders")
    @GetMapping
    public List<CustomerOrder> getAllOrders() {
        return orderService.getAllOrders();
    }


    @Operation(summary = "Create a new order", description = "Create a new customer order")
    @PostMapping
    public CustomerOrder createOrder(@RequestBody CustomerOrder order) {
        return orderService.createOrder(order);
    }

    @Operation(summary = "Delete an order", description = "Delete an order by its ID")
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<CustomerOrder> closeOrder(@PathVariable Long id) {
        CustomerOrder closedOrder = orderService.closeOrder(id);
        return ResponseEntity.ok(closedOrder);
    }

    @GetMapping("/total-sales")
    public double getTotalSales() {
        return orderService.calculateTotalSales();
    }

    @PostMapping("/{orderId}/add-item")
    public ResponseEntity<CustomerOrder> addItemToOrder(
            @PathVariable Long orderId,
            @RequestBody OrderItem orderItem) {
        CustomerOrder updatedOrder = orderService.addItemToOrder(orderId, orderItem);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{orderId}/checkout")
    public ResponseEntity<CustomerOrder> checkoutOrder(@PathVariable Long orderId) {
        CustomerOrder closedOrder = orderService.checkoutOrder(orderId);
        return ResponseEntity.ok(closedOrder);
    }
    @GetMapping("/orders/user/{userId}/all-orders")
    public ResponseEntity<List<CustomerOrder>> getUserOrders(@PathVariable Long userId) {
        List<CustomerOrder> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/orders/user/{userId}/specific-orders")
    public ResponseEntity<List<CustomerOrder>> getOrdersByUserId(@PathVariable Long userId) {
        List<CustomerOrder> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);


    }

    @GetMapping("/history")
    public List<CustomerOrder> getOrderHistory(@AuthenticationPrincipal AppUser user) {
        return orderService.getOrdersByUserId(user.getId());
    }









}

