//
//package com.skc.library.security;
//
//import java.util.Collection;
//
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.skc.library.model.User;
//
//public class JwtUser implements UserDetails{
//
//    private static final long serialVersionUID = 1L;
//
//    private final Long id;
//    private final String username;
//    private final String password;
//    private final User user;
//    private final Collection<? extends GrantedAuthority> authorities;
//    private final boolean enabled;
//
//    public JwtUser(Long id, String username, String password, User user,
//                   Collection<? extends GrantedAuthority> authorities, boolean enabled) {
//        super();
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.user = user;
//        this.authorities = authorities;
//        this.enabled = enabled;
//    }
//
//
//
//    @JsonIgnore
//    public Long getId() {
//        return id;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//}
