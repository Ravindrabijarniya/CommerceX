package com.ravindra.commercex.security.userdetails;

import com.ravindra.commercex.auth.entity.Role;
import com.ravindra.commercex.auth.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public final class CustomUserDetails implements UserDetails {

    private final User user;



    public CustomUserDetails(User user){
        this.user = user;
    }


    @Override
    public String getUsername(){

        return user.getEmail();

    }

    public Long getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public Set<String> getRoleNames() {
        return user.getRoles()
            .stream()
            .map(Role::getName)
            .collect(Collectors.toSet());
    }

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> authorities = new HashSet<>();

        // Roles
        user.getRoles().forEach(role ->

            authorities.add(
                new SimpleGrantedAuthority(
                    role.getName()
                )
            )

        );

        // Permissions
        user.getRoles().forEach(role ->

            role.getPermissions().forEach(permission ->

                authorities.add(

                    new SimpleGrantedAuthority(

                        permission.getName()

                    )

                )

            )

        );

        return authorities;

    }


    @Override
    public String getPassword(){

        return user.getPassword();

    }


    @Override
    public boolean isAccountNonLocked(){

        return !user.isAccountLocked();

    }

    @Override
    public boolean isEnabled(){

        return user.isEnabled();

    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.isAccountExpired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.isCredentialsExpired();
    }

    public User getUser() {
        return user;
    }
}
