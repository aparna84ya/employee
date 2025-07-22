package com.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDTOResponse {
    private String deptId;
    private String deptName;
    private String description;
    private List<EmployeeDTOResponse> employeeDTOResponses;
}
