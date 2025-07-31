package com.employee.controller;

import com.employee.dto.DepartmentDTOPageResponse;
import com.employee.dto.EmployeeDTOPageResponse;
import com.employee.dto.EmployeeDTOResponse;
import com.employee.service.EmployeeService;
import com.employee.dto.EmployeeDTORequest;
import com.employee.exception.DepartmentNotFoundException;
import com.employee.exception.EmployeeNotFoundException;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTORequest employeeDTORequest) {
        try {
            EmployeeDTOResponse employeeDTOResponse = employeeService.addEmployee(employeeDTORequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(employeeDTOResponse);
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getEmployees() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.GetAllEmployees());
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{empId}")
    public ResponseEntity<?> getEmployee(@PathVariable String empId){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeByEmpId(empId));
        } catch (DepartmentNotFoundException | EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<?> getEmployeeByFirstName(@PathVariable String firstName){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeByFirstName(firstName));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> getEmployeeByEmail(@PathVariable String email){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeByEmail(email));
        }
        catch (EmployeeNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<?> getEmployeeByAddress(@PathVariable String address){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeByAddress(address));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{empId}")
    public ResponseEntity<?> updateEmployee( @PathVariable String empId, @RequestBody EmployeeDTORequest employeeDTORequest){
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
        try{
            String responseMessage = employeeService.updateEmployeeFirstName(empId,firstName);
            return ResponseEntity.ok(responseMessage);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/firstAndLastName/{empId}/{firstName}/{lastName}")
    public ResponseEntity<?> updateEmployeeFirstAndLastName(@PathVariable String empId,
                                                         @PathVariable String firstName,
                                                         @PathVariable String lastName) throws EmployeeNotFoundException{
        try {
            String responseMessage = employeeService.updateEmployeeFirstAndLastName(empId, firstName, lastName);
            return ResponseEntity.ok(responseMessage);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }

    @GetMapping("/employeeByPage/{page}/{size}")
    public EmployeeDTOPageResponse getEmployeeByPage(
            @PathVariable int page,
            @PathVariable int size) {
        return employeeService.getEmployeeByPage(page, size);
    }

    @GetMapping("/employeeByPage/{page}/{size}/{property}")
    public EmployeeDTOPageResponse getEmployeeByPageAndAscByProperty(
            @PathVariable int page,
            @PathVariable int size,
            @PathVariable String property) {
        return employeeService.getEmployeeByPageAndAscByProperty(page, size, property);
    }

    @DeleteMapping("/{empId}")
    public String deleteEmployee(@PathVariable String empId){
        return employeeService.deleteEmployee(empId);
    }

    @GetMapping("/employees/firstName")
    public ResponseEntity<List<String>> getEmployeesFirstName(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getEmployeesFirstName());
    }
}
