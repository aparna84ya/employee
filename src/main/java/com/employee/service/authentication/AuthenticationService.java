package com.employee.service.authentication;

import com.employee.dto.authentication.AuthenticationDTOResponse;
import com.employee.dto.employee.EmployeeDTOResponse;
import com.employee.dto.employee.EmployeeLoginDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    AuthenticationDTOResponse login(@RequestBody EmployeeLoginDTO employeeLoginDTO,
                                    HttpServletRequest request,
                                    HttpServletResponse response);
}
