package com.ravindra.commercex.auth.entity;

import com.ravindra.commercex.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table(
    name = "roles",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
    }
)
@Getter
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)

    @JoinTable(

        name = "role_permissions",

        joinColumns =

        @JoinColumn(name = "role_id"),

        inverseJoinColumns =

        @JoinColumn(name = "permission_id")

    )

    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();
}
