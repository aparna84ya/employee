package com.employee.dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTOPageResponse {
    private List<DepartmentDTOResponse> departmentDTOResponses;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
}
