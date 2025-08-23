package com.employee.repository.department;

import com.employee.model.department.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {

    List<Department> findByDeptName(String deptName);
}
