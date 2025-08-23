package com.employee.service.employee;

import com.employee.dto.employee.EmployeeDTORequest;
import com.employee.dto.employee.EmployeeDTOResponse;
import com.employee.exception.department.DepartmentNotFoundException;
import com.employee.exception.employee.EmployeeNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeDTOResponse addEmployee(EmployeeDTORequest employeeDTORequest) throws DepartmentNotFoundException;

    EmployeeDTOResponse getEmployeeByEmpId(String empId) throws EmployeeNotFoundException, DepartmentNotFoundException;

    List<EmployeeDTOResponse> getEmployeeByFirstName(String firstName) throws DepartmentNotFoundException;

    List<EmployeeDTOResponse> getEmployeeByAddress(String address) throws DepartmentNotFoundException;

    EmployeeDTOResponse updateEmployee(String empId, EmployeeDTORequest employeeDTORequest)
            throws EmployeeNotFoundException, DepartmentNotFoundException;

    String updateEmployeeFirstName(String empId, String firstName) throws EmployeeNotFoundException;

    String deleteEmployee(String empId);

    String updateEmployeeFirstAndLastName(String empId, String firstName, String lastName) throws EmployeeNotFoundException;

    List<String> getEmployeesFirstName();

    EmployeeDTOResponse getEmployeeByEmail(String email) throws DepartmentNotFoundException, EmployeeNotFoundException;

    //Page<Employee> getEmployeeByPageAndAscByProperty(int page, int size, String property);

    Page<EmployeeDTOResponse> getEmployeeByPage(int pageNumber, int pageSize, String sortFieldName, String sortDirection) throws DepartmentNotFoundException;
}
