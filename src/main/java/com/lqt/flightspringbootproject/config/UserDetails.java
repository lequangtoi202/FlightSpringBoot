package com.lqt.flightspringbootproject.config;

import com.lqt.flightspringbootproject.model.User;
import com.lqt.flightspringbootproject.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
