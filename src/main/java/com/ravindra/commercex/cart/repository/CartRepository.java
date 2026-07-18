package com.ravindra.commercex.cart.repository;

import com.ravindra.commercex.cart.entity.Cart;
import com.ravindra.commercex.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

    boolean existsByUser(User user);

}
