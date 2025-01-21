package com.shoppingwebsite.shoppingbackend.repository;


import com.shoppingwebsite.shoppingbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
}


