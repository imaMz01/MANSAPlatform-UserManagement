package com.mansa.user.Entities;


import com.mansa.user.Dtos.RoleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String tel;
    private String email;
    private String password;
    private String address;
    private boolean enabled;
    private boolean emailVerified;
    private LocalDateTime created;
    private LocalDateTime updated;

    @ElementCollection( fetch = FetchType.EAGER)
    @JoinTable(name = "userRoles", joinColumns = @JoinColumn(name = "idUser"))
    @Column(name = "role", nullable = false)
    private Set<String> role = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream().map(
                t -> new SimpleGrantedAuthority(t.toString())
        ).collect(Collectors.toList());

    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
