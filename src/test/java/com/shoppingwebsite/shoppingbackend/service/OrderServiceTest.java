package com.shoppingwebsite.shoppingbackend.service;

import com.shoppingwebsite.shoppingbackend.model.CustomerOrder;
import com.shoppingwebsite.shoppingbackend.model.OrderItem;
import com.shoppingwebsite.shoppingbackend.model.Product;
import com.shoppingwebsite.shoppingbackend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    private CustomerOrder order;
    private OrderItem orderItem;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        product = new Product();
        product.setId(1L);
        product.setStock(10);
        product.setPrice(100.0);

        orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(2);

        order = new CustomerOrder();
        order.setItems(Collections.singletonList(orderItem));
    }

    @Test
    void testCalculateTotal() {

        when(productService.getProductById(1L)).thenReturn(product);

        double total = orderService.calculateTotal(order);
        assertEquals(200.0, total);
    }

    @Test
    void testSaveOrder_ValidStock() {

        when(productService.getProductById(1L)).thenReturn(product);
        when(orderRepository.save(order)).thenReturn(order);

        CustomerOrder savedOrder = orderService.saveOrder(order);
        assertEquals(200.0, savedOrder.getTotal());
    }

    @Test
    void testSaveOrder_InsufficientStock() {

        product.setStock(1);
        when(productService.getProductById(1L)).thenReturn(product);


        assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(order));
    }
}
