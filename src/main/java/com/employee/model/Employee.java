package com.employee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    private String empId ;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    @CreatedDate
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;

    private String deptId;
}

