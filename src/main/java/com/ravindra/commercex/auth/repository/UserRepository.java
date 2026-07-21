package com.ravindra.commercex.auth.repository;


import com.ravindra.commercex.auth.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository
    extends JpaRepository<User, Long> {


    @EntityGraph(
        attributePaths = {
            "roles",
            "roles.permissions"
        }
    )
    Optional<User> findByEmail(String email);


    boolean existsByEmail(String email);


    Page<User> findAllByOrderByCreatedAtDesc(
        Pageable pageable
    );

}
