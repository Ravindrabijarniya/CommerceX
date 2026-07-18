package com.ravindra.commercex.address.entity;

import com.ravindra.commercex.address.exception.InvalidAddressException;
import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "addresses",
    indexes = {
        @Index(name = "idx_addresses_user", columnList = "user_id"),
        @Index(name = "idx_addresses_user_default", columnList = "user_id, default_address"),
        @Index(name = "idx_addresses_user_active", columnList = "user_id, active")
    }
)
public class Address extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_addresses_user")
    )
    private User user;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "address_line1", nullable = false, length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Column(nullable = false, length = 100)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false, length = 20)
    private AddressType addressType;

    @Column(name = "default_address", nullable = false)
    private boolean defaultAddress;

    @Column(nullable = false)
    private boolean active;



    public static Address create(
        User user,
        String fullName,
        String phoneNumber,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country,
        AddressType addressType,
        boolean defaultAddress
    ) {

        validate(
            user,
            fullName,
            phoneNumber,
            addressLine1,
            city,
            state,
            postalCode,
            country,
            addressType
        );

        return Address.builder()
            .user(user)
            .fullName(fullName.trim())
            .phoneNumber(phoneNumber.trim())
            .addressLine1(addressLine1.trim())
            .addressLine2(addressLine2 == null ? null : addressLine2.trim())
            .city(city.trim())
            .state(state.trim())
            .postalCode(postalCode.trim())
            .country(country.trim())
            .addressType(addressType)
            .defaultAddress(defaultAddress)
            .active(true)
            .build();
    }



    public void update(
        String fullName,
        String phoneNumber,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country,
        AddressType addressType
    ) {

        validate(
            user,
            fullName,
            phoneNumber,
            addressLine1,
            city,
            state,
            postalCode,
            country,
            addressType
        );

        this.fullName = fullName.trim();
        this.phoneNumber = phoneNumber.trim();
        this.addressLine1 = addressLine1.trim();
        this.addressLine2 = addressLine2 == null ? null : addressLine2.trim();
        this.city = city.trim();
        this.state = state.trim();
        this.postalCode = postalCode.trim();
        this.country = country.trim();
        this.addressType = addressType;
    }

    public void markAsDefault() {
        this.defaultAddress = true;
    }

    public void removeDefault() {
        this.defaultAddress = false;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }


    public boolean belongsTo(User user) {
        return this.user.equals(user);
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public boolean isActive() {
        return active;
    }


    private static void validate(
        User user,
        String fullName,
        String phoneNumber,
        String addressLine1,
        String city,
        String state,
        String postalCode,
        String country,
        AddressType addressType
    ) {

        if (user == null) {
            throw new InvalidAddressException("User must not be null.");
        }
        requireText(fullName, "Full name");
        requireText(phoneNumber, "Phone number");
        requireText(addressLine1, "Address line 1");
        requireText(city, "City");
        requireText(state, "State");
        requireText(postalCode, "Postal code");
        requireText(country, "Country");
        Objects.requireNonNull(addressType, "Address type must not be null.");
    }

    private static void requireText(String value, String fieldName) {

        if (value == null || value.isBlank()) {
            throw new InvalidAddressException(fieldName + " must not be blank.");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address address)) {
            return false;
        }
        return getId() != null && getId().equals(address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
