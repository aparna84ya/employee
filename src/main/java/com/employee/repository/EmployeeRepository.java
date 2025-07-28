package com.employee.repository;

import com.employee.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByFirstName(String firstName);
    List<Employee> findByAddress(String address);
    List<Employee> findByDeptId(String deptId);

   List<Employee> findByEmail(String email);
}
