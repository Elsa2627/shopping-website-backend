package com.shoppingwebsite.shoppingbackend.controller;

import com.shoppingwebsite.shoppingbackend.repository.OrderRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.shoppingwebsite.shoppingbackend.service.OrderService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService OrderService;


    @MockBean
    private OrderRepository OrderRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void testCreateOrder() throws Exception {
        mockMvc.perform(post("/orders")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"total\":100}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUpdateOrder() throws Exception {
        mockMvc.perform(put("/orders/1")
                        .with(csrf())  // Ajout du CSRF token
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\":120}"))
                .andExpect(status().isOk());
    }


}
