package com.employee.service;

import com.employee.dto.EmployeeDTORequest;
import com.employee.dto.EmployeeDTOResponse;
import com.employee.exception.DepartmentNotFoundException;
import com.employee.exception.EmployeeNotFoundException;
import java.util.List;

public interface EmployeeService {

    EmployeeDTOResponse addEmployee(EmployeeDTORequest employeeDTORequest) throws DepartmentNotFoundException;

    List<EmployeeDTOResponse> GetAllEmployees() throws DepartmentNotFoundException;

    EmployeeDTOResponse getEmployeeByEmpId(String empId) throws EmployeeNotFoundException, DepartmentNotFoundException;

    List<EmployeeDTOResponse> getEmployeeByFirstName(String firstName) throws DepartmentNotFoundException;

    List<EmployeeDTOResponse> getEmployeeByAddress(String address) throws DepartmentNotFoundException;

    EmployeeDTOResponse updateEmployee(String empId, EmployeeDTORequest employeeDTORequest)
            throws EmployeeNotFoundException, DepartmentNotFoundException;

    String updateEmployeeFirstName(String empId, String firstName) throws EmployeeNotFoundException;

    String deleteEmployee(String empId);

    String updateEmployeeFirstAndLastName(String empId, String firstName, String lastName) throws EmployeeNotFoundException ;

    List<String> getEmployeesFirstName();

    String getEmployeeByEmail(String empId) throws EmployeeNotFoundException;
}
