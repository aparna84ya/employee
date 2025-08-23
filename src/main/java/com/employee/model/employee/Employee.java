package com.employee.model.employee;

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
    private String email;
    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedDate
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
    private String password;
    private boolean enabled;
    private String deptId;
}

