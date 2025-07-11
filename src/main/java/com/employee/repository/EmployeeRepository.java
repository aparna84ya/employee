package com.employee.repository;

import com.employee.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee , String> {
    List<Employee> findByFirstName(String firstName);
    List<Employee> findByAddress(String address);
}
