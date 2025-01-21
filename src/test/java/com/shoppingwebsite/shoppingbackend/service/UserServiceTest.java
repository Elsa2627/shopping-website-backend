package com.shoppingwebsite.shoppingbackend.service;

import com.shoppingwebsite.shoppingbackend.model.AppUser;
import com.shoppingwebsite.shoppingbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        OrderService orderService = mock(OrderService.class);  // Ajout
        ProductService productService = mock(ProductService.class);
        userService = new UserService(userRepository, passwordEncoder, orderService, productService);

    }

    @Test
    void testSaveUser() {
        AppUser user = new AppUser(null, "test@example.com", "password", "John", "Doe");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        userService.saveUser(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAuthenticateUser_Success() {
        AppUser user = new AppUser(null, "test@example.com", "encodedPassword", "John", "Doe");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        boolean isAuthenticated = userService.authenticateUser(new AppUser(null, "test@example.com", "password", null, null));

        assertTrue(isAuthenticated);
    }
}
