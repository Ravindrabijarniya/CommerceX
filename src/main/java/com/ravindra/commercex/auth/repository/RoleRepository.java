package com.ravindra.commercex.auth.repository;

import com.ravindra.commercex.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository
    extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
