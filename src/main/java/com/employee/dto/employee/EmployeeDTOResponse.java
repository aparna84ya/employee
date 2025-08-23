package com.employee.dto.employee;

import com.employee.dto.department.DepartmentDTOResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTOResponse {
    private String empId ;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String address;
    private DepartmentDTOResponse departmentDTOResponse;
}