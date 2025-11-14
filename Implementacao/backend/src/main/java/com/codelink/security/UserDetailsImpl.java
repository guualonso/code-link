package com.codelink.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.codelink.model.Usuario;

public class UserDetailsImpl implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String username; 
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled = true;

    public UserDetailsImpl(Usuario u) {
        this.id = u.getId();
        this.username = u.getEmail();
        this.password = u.getSenha();
        this.authorities = List.of(new SimpleGrantedAuthority(u.getRole().name()));
    }

    public Long getId() { return id; }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return enabled; }
}
