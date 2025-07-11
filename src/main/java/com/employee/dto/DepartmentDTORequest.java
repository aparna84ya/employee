package com.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
public class DepartmentDTORequest {

    @Id
    private String deptId;
    private String deptName;
    private String description;

}
