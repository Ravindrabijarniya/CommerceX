package com.ravindra.commercex.order.repository;


import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.order.entity.Order;
import com.ravindra.commercex.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OrderRepository
    extends JpaRepository<Order,Long> {


    List<Order> findAllByUserOrderByCreatedAtDesc(
        User user
    );


    Optional<Order> findByIdAndUser(
        Long id,
        User user
    );


    Optional<Order> findByOrderNumber(
        String orderNumber
    );


    boolean existsByOrderNumber(
        String orderNumber
    );


    List<Order> findAllByStatus(
        OrderStatus status
    );



    @EntityGraph(
        attributePaths = {
            "user",
            "items"
        }
    )
    Page<Order> findAll(
        Pageable pageable
    );



    @EntityGraph(
        attributePaths = {
            "user",
            "items"
        }
    )
    Optional<Order> findDetailedById(
        Long id
    );

}
