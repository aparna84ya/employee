package com.employee.dto;

import lombok.Data;

@Data
public class EmployeeDTOResponse {
    private String empId ;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;

    private DepartmentDTOResponse departmentDTOResponse;
}
