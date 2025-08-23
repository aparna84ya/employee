package com.employee.dto.authentication;

import com.employee.dto.employee.EmployeeDTOResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTOResponse {
    private EmployeeDTOResponse employeeDTOResponse;
    private String token;
}
