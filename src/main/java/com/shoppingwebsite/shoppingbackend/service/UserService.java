package com.shoppingwebsite.shoppingbackend.service;

import com.shoppingwebsite.shoppingbackend.exeption.EmailAlreadyInUseException;
import com.shoppingwebsite.shoppingbackend.exeption.UserNotFoundException;
import com.shoppingwebsite.shoppingbackend.model.AppUser;
import com.shoppingwebsite.shoppingbackend.model.CustomerOrder;
import com.shoppingwebsite.shoppingbackend.model.Product;
import com.shoppingwebsite.shoppingbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;
    private final ProductService productService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, OrderService orderService, ProductService productService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
        this.productService = productService;
    }


    public boolean saveUser(AppUser user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("This email is already in use.");
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }


    public boolean authenticateUser(AppUser user) {
        Optional<AppUser> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User not found with email: " + user.getEmail());
        }

        boolean isAuthenticated = passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword());
        if (isAuthenticated) {
            System.out.println("User authenticated: " + user.getEmail());
        }
        return isAuthenticated;
    }

    public boolean deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
        System.out.println("User deleted with ID: " + userId);
        return true;
    }


    public CustomerOrder closeOrder(Long orderId) {
        CustomerOrder order = orderService.getOrderById(orderId);
        order.setStatus(CustomerOrder.OrderStatus.CLOSED);
        return orderService.saveOrder(order);
    }


    public boolean addFavorite(Long userId, Long productId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Product product = productService.getProductById(productId);

        if (user.getFavorites() == null) {
            user.setFavorites(new ArrayList<>());
        }

        if (!user.getFavorites().contains(product)) {
            user.getFavorites().add(product);
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public List<Product> getFavorites(Long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        return user.getFavorites() != null ? user.getFavorites() : new ArrayList<>();
    }


    public boolean removeFavorite(Long userId, Long productId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (user.getFavorites() != null && user.getFavorites().removeIf(product -> product.getId().equals(productId))) {
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
