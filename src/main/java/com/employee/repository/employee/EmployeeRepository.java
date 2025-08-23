package com.employee.repository.employee;

import com.employee.model.employee.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByFirstName(String firstName);
    List<Employee> findByAddress(String address);
    List<Employee> findByDeptId(String deptId);

   Optional<Employee> findByEmail(String email);

    List <Employee>getEmployeeByFirstName(String firstName);

    Employee findByEmailIgnoreCase(String email);
}
