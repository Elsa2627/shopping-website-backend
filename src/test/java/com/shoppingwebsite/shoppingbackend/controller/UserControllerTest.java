package com.shoppingwebsite.shoppingbackend.controller;

import com.shoppingwebsite.shoppingbackend.model.AppUser;
import com.shoppingwebsite.shoppingbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void testSignup() throws Exception {
        doNothing().when(userService).saveUser(any(AppUser.class)); // Utiliser doNothing pour des méthodes void

        mockMvc.perform(post("/users/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"password\", \"firstName\":\"John\", \"lastName\":\"Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully."));
    }


    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    public void testLogin_Success() throws Exception {
        AppUser mockUser = new AppUser(null, "test@example.com", "password", null, null);
        when(userService.authenticateUser(any(AppUser.class))).thenReturn(true);

        mockMvc.perform(post("/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful."));
    }


    @Test
    @WithMockUser
    public void testLogin_Failure() throws Exception {
        when(userService.authenticateUser(any(AppUser.class))).thenReturn(false);

        mockMvc.perform(post("/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"wrong password\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials."));
    }

    @Test
    public void testSaveUserWithDuplicateEmail() {
        AppUser user1 = new AppUser(null, "test@example.com", "password123", "John", "Doe");
        userService.saveUser(user1);

        AppUser user2 = new AppUser(null, "test@example.com", "password456", "Jane", "Doe");
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user2));
    }

}
