package com.ravindra.commercex.auth.entity;


import com.ravindra.commercex.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(
    name = "users",
    indexes = {
        @Index(
            name = "idx_user_email",
            columnList = "email"
        )
    },
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = "email"
        )
    }
)
public class User extends BaseEntity {


    @NotBlank
    @Size(max = 50)
    private String firstName;


    @NotBlank
    @Size(max = 50)
    private String lastName;


    @NotBlank
    @Email
    @Size(max = 255)
    private String email;


    @NotBlank
    private String password;


    private boolean enabled;

    private boolean accountLocked;

    private boolean accountExpired;

    private boolean credentialsExpired;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns =
        @JoinColumn(name = "user_id"),

        inverseJoinColumns =
        @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();



    public static User create(
        String firstName,
        String lastName,
        String email
    ){

        return User.builder()

            .firstName(
                requireText(firstName)
            )

            .lastName(
                requireText(lastName)
            )

            .email(
                requireText(email)
            )

            .enabled(true)

            .accountLocked(false)

            .accountExpired(false)

            .credentialsExpired(false)

            .build();
    }



    public void updateProfile(
        String firstName,
        String lastName
    ){

        this.firstName =
            requireText(firstName);


        this.lastName =
            requireText(lastName);

    }



    public void changePassword(
        String encodedPassword
    ){

        this.password =
            Objects.requireNonNull(
                encodedPassword,
                "Password cannot be null"
            );

    }



    public void addRole(Role role){

        roles.add(
            Objects.requireNonNull(
                role,
                "Role cannot be null"
            )
        );

    }


    public void disableAccount(){

        this.enabled = false;

    }

    public void enableAccount(){

        this.enabled = true;

    }

    public void lockAccount(){

        this.accountLocked = true;

    }

    public void unlockAccount(){

        this.accountLocked = false;

    }


    public boolean hasRole(
        String roleName
    ){

        return roles.stream()
            .anyMatch(
                role ->
                    role.getName()
                        .equals(roleName)
            );

    }



    private static String requireText(
        String value
    ){

        Objects.requireNonNull(
            value,
            "Value cannot be null"
        );


        if(value.isBlank()){

            throw new IllegalArgumentException(
                "Value cannot be blank"
            );

        }


        return value.trim();

    }

    public boolean isActive(){

        return enabled && !accountLocked;

    }

}
