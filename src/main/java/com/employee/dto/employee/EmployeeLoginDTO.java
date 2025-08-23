package com.employee.dto.employee;

import lombok.Data;

import java.util.Set;

@Data
public class EmployeeLoginDTO {
    private String email;
    private String password;
}
