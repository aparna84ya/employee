package com.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTOPageResponse {

    private List<EmployeeDTOResponse> employeeDTOResponse;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
}
