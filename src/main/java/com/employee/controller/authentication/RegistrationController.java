package com.employee.controller.authentication;

import com.employee.dto.employee.EmployeeDTORequest;
import com.employee.dto.employee.EmployeeDTOResponse;
import com.employee.exception.department.DepartmentNotFoundException;
import com.employee.exception.employee.EmployeeNotFoundException;
import com.employee.model.employee.Employee;
import com.employee.repository.employee.EmployeeRepository;
import com.employee.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping(value="/api/registration")
    public ResponseEntity<?> registration(@RequestBody EmployeeDTORequest employeeDTORequest) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findByEmailIgnoreCase(employeeDTORequest.getEmail());

        if(employee == null) {
            try {
                EmployeeDTOResponse employeeDTOResponse = employeeService.addEmployee(employeeDTORequest);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(employeeDTOResponse);
            } catch (DepartmentNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(employee.getEmail() + " is already registered.");
    }
}