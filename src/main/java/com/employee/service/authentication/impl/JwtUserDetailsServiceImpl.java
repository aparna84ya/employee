package com.employee.service.authentication.impl;

import com.employee.dto.employee.EmployeeDTOResponse;
import com.employee.exception.department.DepartmentNotFoundException;
import com.employee.exception.employee.EmployeeNotFoundException;
import com.employee.model.department.Department;
import com.employee.security.JwtEmployeeFactory;
import com.employee.model.employee.Employee;
import com.employee.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmailIgnoreCase(username);
        if (employee == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtEmployeeFactory.create(employee);
        }

    }

//    public EmployeeDTOResponse getEmployeeByEmail(String email) throws DepartmentNotFoundException, EmployeeNotFoundException {
//        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
//        if (optionalEmployee.isPresent()) {
//            Optional<Department> optionalDepartment = employeeRepository.findByEmail(optionalEmployee.get().getDeptId());
//            if (!optionalDepartment.isPresent()) {
//                throw new DepartmentNotFoundException(optionalEmployee.get().getDeptId() + " is not exist in database.");
//            }
//            return prepareEmployeeDTOResponse(optionalEmployee.get(), optionalDepartment.get());
//        }
//        throw new EmployeeNotFoundException(email + " not found in database");
//    }
}