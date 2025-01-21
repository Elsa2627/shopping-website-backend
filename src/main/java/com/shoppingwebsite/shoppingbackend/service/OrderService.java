package com.shoppingwebsite.shoppingbackend.service;

import com.shoppingwebsite.shoppingbackend.model.CustomerOrder;
import com.shoppingwebsite.shoppingbackend.model.OrderItem;
import com.shoppingwebsite.shoppingbackend.model.Product;
import com.shoppingwebsite.shoppingbackend.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public CustomerOrder createOrder(CustomerOrder order) {
        return orderRepository.save(order);
    }






    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public double calculateTotal(CustomerOrder order) {
        return order.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }


    @Transactional
    public CustomerOrder saveOrder(CustomerOrder order) {
        validateStock(order); // Check stock before saving
        order.setTotal(calculateTotal(order)); // Calculate total price
        return orderRepository.save(order);
    }

    @Transactional
    public CustomerOrder updateOrder(Long id, CustomerOrder order) {
        Optional<CustomerOrder> existingOrder = orderRepository.findById(id);
        if (existingOrder.isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + id + " does not exist.");
        }
        validateStock(order);
        order.setId(id);
        order.setTotal(calculateTotal(order));
        return orderRepository.save(order);
    }

    public List<CustomerOrder> getOrdersByStatus(CustomerOrder.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }


    public CustomerOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + id + " not found."));
    }


    private void validateStock(CustomerOrder order) {
        for (OrderItem item : order.getItems()) {
            Product product = productService.getProductById(item.getProduct().getId());
            if (product.getStock() < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - item.getQuantity());
            productService.updateProduct(product);
        }
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order with ID " + id + " does not exist.");
        }
        orderRepository.deleteById(id);
    }

    public List<CustomerOrder> getOrdersByUserId(Long userId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }


    public CustomerOrder closeOrder(Long orderId) {
        CustomerOrder order = getOrderById(orderId);
        order.setStatus(CustomerOrder.OrderStatus.CLOSED);
        return orderRepository.save(order);
    }

    public double calculateTotalSales() {
        List<CustomerOrder> closedOrders = orderRepository.findByStatus(CustomerOrder.OrderStatus.CLOSED);
        return closedOrders.stream()
                .mapToDouble(CustomerOrder::getTotal)
                .sum();
    }

    @Transactional
    public CustomerOrder addItemToOrder(Long orderId, OrderItem orderItem) {
        CustomerOrder order = getOrderById(orderId);
        if (!order.getStatus().equals(CustomerOrder.OrderStatus.TEMP)) {
            throw new IllegalStateException("Cannot modify a non-TEMP order.");
        }
        order.addOrderItem(orderItem);
        return orderRepository.save(order);
    }

    @Transactional
    public CustomerOrder checkoutOrder(Long orderId) {
        CustomerOrder order = getOrderById(orderId);
        if (!order.getStatus().equals(CustomerOrder.OrderStatus.TEMP)) {
            throw new IllegalStateException("Only TEMP orders can be checked out.");
        }
        validateStock(order);
        order.setStatus(CustomerOrder.OrderStatus.CLOSED);
        return orderRepository.save(order);
    }






}
