package com.employee.controller.employee;

import com.employee.dto.employee.EmployeeDTOResponse;
import com.employee.model.employee.Employee;
import com.employee.repository.employee.EmployeeRepository;
import com.employee.service.employee.EmployeeService;
import com.employee.dto.employee.EmployeeDTORequest;
import com.employee.exception.department.DepartmentNotFoundException;
import com.employee.exception.employee.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTORequest employeeDTORequest) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findByEmailIgnoreCase(employeeDTORequest.getEmail());

        if (employee == null) {
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

    @PutMapping("/{empId}")
    public ResponseEntity<?> updateEmployee(@PathVariable String empId, @RequestBody EmployeeDTORequest employeeDTORequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.updateEmployee(empId, employeeDTORequest));
        } catch (DepartmentNotFoundException | EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/firstName/{empId}/{firstName}")
    public ResponseEntity<String> updateEmployeeFirstName
            (@PathVariable String empId, @PathVariable String firstName) throws EmployeeNotFoundException {
        try {
            String responseMessage = employeeService.updateEmployeeFirstName(empId, firstName);
            return ResponseEntity.ok(responseMessage);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/firstAndLastName/{empId}/{firstName}/{lastName}")
    public ResponseEntity<?> updateEmployeeFirstAndLastName(@PathVariable String empId,
                                                            @PathVariable String firstName,
                                                            @PathVariable String lastName) throws EmployeeNotFoundException {
        try {
            String responseMessage = employeeService.updateEmployeeFirstAndLastName(empId, firstName, lastName);
            return ResponseEntity.ok(responseMessage);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }

    @GetMapping("/{empId}")
    public ResponseEntity<?> getEmployee(@PathVariable String empId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeByEmpId(empId));
        } catch (DepartmentNotFoundException | EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getEmployees(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "firstName") String sortFieldName,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection) {
        try {
            if (pageNumber < 0 || pageSize <= 0) {
                return ResponseEntity.badRequest()
                        .body("Page number must be >= 0 and page size must be > 0");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeByPage(pageNumber, pageSize, sortFieldName, sortDirection));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<?> getEmployeesByFirstName(@PathVariable String firstName) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeByFirstName(firstName));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getEmployeeByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeByEmail(email));
        } catch (DepartmentNotFoundException | EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<?> getEmployeesByAddress(@PathVariable String address) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeByAddress(address));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/employees/firstName")
    public ResponseEntity<List<String>> getEmployeesFirstName() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getEmployeesFirstName());
    }

    @DeleteMapping("/{empId}")
    public String deleteEmployee(@PathVariable String empId) {
        return employeeService.deleteEmployee(empId);
    }
}
