package com.shoppingwebsite.shoppingbackend.repository;

import com.shoppingwebsite.shoppingbackend.model.CustomerOrder;
import com.shoppingwebsite.shoppingbackend.model.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByStatus() {

        AppUser user = new AppUser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");

        user = userRepository.save(user);


        CustomerOrder order = new CustomerOrder();
        order.setStatus(CustomerOrder.OrderStatus.PENDING);
        order.setUser(user);
        orderRepository.save(order);

        List<CustomerOrder> orders = orderRepository.findByStatus(CustomerOrder.OrderStatus.PENDING);

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getStatus()).isEqualTo(CustomerOrder.OrderStatus.PENDING);
        assertThat(orders.get(0).getUser()).isEqualTo(user);
    }
}

