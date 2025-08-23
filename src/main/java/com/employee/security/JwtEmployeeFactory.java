package com.employee.security;

import com.employee.model.employee.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEmployeeFactory {

    public static JwtEmployee create(Employee employee) {

        return new JwtEmployee(employee.getEmpId(), employee.getEmail(), employee.getPassword(), employee, mapToGrantedAuthorities (Collections.singletonList("ROLE_" + employee.getEmail())),
                employee.isEnabled());
    }

    private static Collection<GrantedAuthority> mapToGrantedAuthorities (List<String> authorities) {
        return authorities.stream().map(Authority -> new SimpleGrantedAuthority(Authority)).collect(Collectors.toList());
    }

}
