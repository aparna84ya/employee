package com.employee.controller.authentication;

import com.employee.dto.authentication.AuthenticationDTOResponse;
import com.employee.exception.authentication.UnauthorizedException;
import com.employee.security.JwtTokenUtil;
import com.employee.service.authentication.AuthenticationService;
import com.employee.dto.employee.EmployeeLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${jwt.header}")//HTTP Header key
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;//Authenticate user credential

    @Autowired
    private JwtTokenUtil jwtTokenUtil;//handles generating and validating JWT Tokens.

    @PostMapping(value = "/api/login")
    public ResponseEntity<AuthenticationDTOResponse> login(@RequestBody EmployeeLoginDTO employeeLoginDTO,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response) {
        try {
            AuthenticationDTOResponse authenticationDTOResponse = authenticationService.login(employeeLoginDTO, request, response);
            return new ResponseEntity<AuthenticationDTOResponse>(authenticationDTOResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());//return a 401 Unauthorized response
        }
    }
}


