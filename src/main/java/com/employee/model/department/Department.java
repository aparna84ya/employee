package com.employee.model.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    private String deptId;
    private String deptName;
    private String description;

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedDate
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
