package com.employee.service.authentication.impl;

import com.employee.dto.authentication.AuthenticationDTOResponse;
import com.employee.security.JwtEmployee;
import com.employee.security.JwtTokenUtil;
import com.employee.service.authentication.AuthenticationService;
import com.employee.dto.department.DepartmentDTOResponse;
import com.employee.dto.employee.EmployeeDTOResponse;
import com.employee.dto.employee.EmployeeLoginDTO;
import com.employee.exception.department.DepartmentNotFoundException;
import com.employee.model.department.Department;
import com.employee.model.employee.Employee;
import com.employee.repository.department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${jwt.header}")//HTTP Header key
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;//Authenticate user credential

    @Autowired
    private JwtTokenUtil jwtTokenUtil;//handles generating and validating JWT Tokens.

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public AuthenticationDTOResponse login(EmployeeLoginDTO employeeLoginDTO,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        employeeLoginDTO.getEmail(), employeeLoginDTO.getPassword()));//Authentication process
        final JwtEmployee employeeDetails = (JwtEmployee) authentication.getPrincipal();//Retrieves the authenticated principal
        SecurityContextHolder.getContext().setAuthentication(authentication);//Stores the authentication object in the Spring SecurityContext.
        final String token = jwtTokenUtil.generateToken(employeeDetails);//generate a JWT token using the authenticated user’s details.
        response.setHeader("Token", token);//Adds the generated JWT token to the response header.

        AuthenticationDTOResponse authenticationDTOResponse = new AuthenticationDTOResponse();
        authenticationDTOResponse.setEmployeeDTOResponse(convertToDTO(employeeDetails.getEmployee()));
        authenticationDTOResponse.setToken(token);

        return authenticationDTOResponse;
    }

    private EmployeeDTOResponse convertToDTO(Employee employee) throws DepartmentNotFoundException {

        Optional<Department> optionalDepartment = departmentRepository.findById(employee.getDeptId());
        if (!optionalDepartment.isPresent()) {
            throw new DepartmentNotFoundException("Department " + employee.getDeptId() + " is not exist in database.");
        }

        DepartmentDTOResponse departmentDTO = new DepartmentDTOResponse();
        Department department = optionalDepartment.get();
        departmentDTO.setDeptId(department.getDeptId());
        departmentDTO.setDeptName(department.getDeptName());
        departmentDTO.setDeptName(department.getDescription());

        EmployeeDTOResponse dto = new EmployeeDTOResponse();
        dto.setEmpId(employee.getEmpId());
        dto.setFirstName(employee.getFirstName());
        dto.setMiddleName(employee.getMiddleName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setAddress(employee.getAddress());
        dto.setDepartmentDTOResponse(departmentDTO);

        return dto;
    }
}
