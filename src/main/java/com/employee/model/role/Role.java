package com.employee.model.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private String roleId ;
    private String role;
    private boolean enabled;
    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedDate
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
