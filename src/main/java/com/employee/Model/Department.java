package com.employee.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    private String deptId;
    private String deptName;
    private String description;

//    private List<Employee> employees;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
}
