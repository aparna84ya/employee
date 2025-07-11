package com.employee.Repository;

import com.employee.Model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DepartmentRepository extends MongoRepository<Department, String> {

    List<Department> findByDeptName(String deptName);
}
