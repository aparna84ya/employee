package com.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTOResponse {
    private String empId ;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private DepartmentDTOResponse departmentDTOResponse;
}