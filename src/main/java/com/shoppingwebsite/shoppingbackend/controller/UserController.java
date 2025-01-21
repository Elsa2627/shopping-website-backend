package com.shoppingwebsite.shoppingbackend.controller;

import com.shoppingwebsite.shoppingbackend.model.AppUser;
import com.shoppingwebsite.shoppingbackend.model.Product;
import com.shoppingwebsite.shoppingbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AppUser user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null || user.getFirstName() == null || user.getLastName() == null) {
                return ResponseEntity.badRequest().body("All fields are required.");
            }

            boolean isRegistered = userService.saveUser(user);
            if (isRegistered) {
                return ResponseEntity.ok("User successfully registered.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occurred.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUser user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password are required.");
        }

        boolean authenticated = userService.authenticateUser(user);
        if (authenticated) {
            return ResponseEntity.ok("Login successful.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }


    @PostMapping("/{userId}/favorites")
    public ResponseEntity<String> addFavorite(@PathVariable Long userId, @RequestParam Long productId) {
        boolean isAdded = userService.addFavorite(userId, productId);
        if (isAdded) {
            return ResponseEntity.ok("Product added to favorites.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found.");
        }
    }


    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Product>> getFavorites(@PathVariable Long userId) {
        List<Product> favorites = userService.getFavorites(userId);
        if (favorites != null) {
            return ResponseEntity.ok(favorites);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/{userId}/favorites/{productId}")
    public ResponseEntity<String> removeFavorite(@PathVariable Long userId, @PathVariable Long productId) {
        boolean isRemoved = userService.removeFavorite(userId, productId);
        if (isRemoved) {
            return ResponseEntity.ok("Product removed from favorites.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found.");
        }
    }
}
