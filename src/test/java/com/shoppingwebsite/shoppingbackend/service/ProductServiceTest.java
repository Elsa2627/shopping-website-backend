package com.shoppingwebsite.shoppingbackend.service;

import com.shoppingwebsite.shoppingbackend.model.Product;
import com.shoppingwebsite.shoppingbackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void testGetProductById_Found() {
        Product product = new Product("Test Product", "Description", 10.0, 100);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);

        assertEquals("Test Product", foundProduct.getName());
    }

    @Test
    void testSaveProduct_Success() {
        Product product = new Product("Product", "Description", 15.0, 10);
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        assertEquals("Product", savedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }
}
