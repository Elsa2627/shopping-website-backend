package com.shoppingwebsite.shoppingbackend.repository;

import com.shoppingwebsite.shoppingbackend.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByStatus(CustomerOrder.OrderStatus status);

    @Query("SELECT o FROM CustomerOrder o WHERE o.user.id = :userId")
    List<CustomerOrder> findAllByUserId(@Param("userId") Long userId);

}

