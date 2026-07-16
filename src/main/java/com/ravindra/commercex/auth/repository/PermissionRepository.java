package com.ravindra.commercex.auth.repository;

import com.ravindra.commercex.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository
    extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);

}
