package com.shoppingwebsite.shoppingbackend.service;

import com.shoppingwebsite.shoppingbackend.model.Product;
import com.shoppingwebsite.shoppingbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;


    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));
    }

    public Product saveProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }



    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product updateProductStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        int currentStock = product.getStock();

        if (quantity < 0 && currentStock + quantity < 0) {
            throw new IllegalArgumentException("Not enough stock available.");
        }
        if (currentStock < quantity) {
            throw new IllegalArgumentException("Not enough stock available.");
        }


        product.setStock(currentStock + quantity);
        return productRepository.save(product);
    }


    public Product updateProductDetails(Long productId, Product newProductDetails) {
        Product existingProduct = getProductById(productId);
        existingProduct.setName(newProductDetails.getName());
        existingProduct.setPrice(newProductDetails.getPrice());
        existingProduct.setStock(newProductDetails.getStock());
        existingProduct.setDescription(newProductDetails.getDescription());
        return productRepository.save(existingProduct);
    }

}
