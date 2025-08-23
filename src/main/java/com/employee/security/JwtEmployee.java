package com.employee.security;

import com.employee.model.employee.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtEmployee implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String username;
    private final String password;
    private final Employee employee;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public JwtEmployee(String id, String username, String password, Employee employee,
                   Collection<? extends GrantedAuthority> authorities, boolean enabled) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.employee = employee;
        this.authorities = authorities;
        this.enabled = enabled;
    }



    @JsonIgnore
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
