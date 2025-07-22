package com.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTORequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String deptId;
}
