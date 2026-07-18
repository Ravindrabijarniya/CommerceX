package com.ravindra.commercex.address.repository;

import com.ravindra.commercex.address.entity.Address;
import com.ravindra.commercex.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUserAndActiveTrue(User user);

    Optional<Address> findByIdAndUserAndActiveTrue(Long addressId, User user);

    Optional<Address> findByUserAndDefaultAddressTrueAndActiveTrue(User user);

    boolean existsByIdAndUserAndActiveTrue(Long addressId, User user);

    List<Address> findAllByUserAndActiveTrueOrderByCreatedAtAsc(User user);

}
