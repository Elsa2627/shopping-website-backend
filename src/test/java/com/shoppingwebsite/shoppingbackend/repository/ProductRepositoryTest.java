package com.shoppingwebsite.shoppingbackend.repository;


import com.shoppingwebsite.shoppingbackend.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindByNameContainingIgnoreCase() {
        Product product = new Product("Test Product", "Description", 100.0, 10);
        productRepository.save(product);

        List<Product> products = productRepository.findByNameContainingIgnoreCase("test");

        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Test Product");
    }
}
