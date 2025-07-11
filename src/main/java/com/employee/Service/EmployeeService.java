package com.employee.Service;

import com.employee.Model.Department;
import com.employee.Model.Employee;
import com.employee.Repository.DepartmentRepository;
import com.employee.Repository.EmployeeRepository;
import com.employee.dto.DepartmentDTOResponse;
import com.employee.dto.EmployeeDTORequest;
import com.employee.dto.EmployeeDTOResponse;
import com.employee.exception.DepartmentNotFoundException;
import com.employee.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service

public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeDTOResponse addEmployee(EmployeeDTORequest employeeDTORequest) throws DepartmentNotFoundException {

        if (Objects.isNull(employeeDTORequest.getDeptId()) || employeeDTORequest.getDeptId().isEmpty()) {
            throw new DepartmentNotFoundException("Dept id is empty.");
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(employeeDTORequest.getDeptId());
        if (!optionalDepartment.isPresent()) {
            throw new DepartmentNotFoundException(employeeDTORequest.getDeptId() + " is not exist in database.");
        }


        Employee employee = new Employee();

        employee.setEmpId(UUID.randomUUID().toString().split("-")[0]);
        employee.setFirstName(employeeDTORequest.getFirstName());
        employee.setMiddleName(employeeDTORequest.getMiddleName());
        employee.setLastName(employeeDTORequest.getLastName());
        employee.setAddress(employeeDTORequest.getAddress());
        employee.setCreatedDate(LocalDateTime.now());
        employee.setUpdatedDate(LocalDateTime.now());
        employee.setCreatedBy(employeeDTORequest.getFirstName());
        employee.setDeptId(employeeDTORequest.getDeptId());


        employee = employeeRepository.save(employee);

        return prepareEmployeeDTOResponse(employee, optionalDepartment.get());
    }

    public List<EmployeeDTOResponse> findAllEmployees() throws DepartmentNotFoundException {
        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeDTOResponse> employeeDTOResponses = new ArrayList<>();

        for (Employee employee : employees) {
            Optional<Department> optionalDepartment = departmentRepository.findById(employee.getDeptId());
            if (!optionalDepartment.isPresent()) {
                throw new DepartmentNotFoundException(employee.getDeptId() + " is not exist in database.");
            }
            EmployeeDTOResponse employeeDTOResponse = prepareEmployeeDTOResponse(employee, optionalDepartment.get());

            employeeDTOResponses.add(employeeDTOResponse);
        }

        return employeeDTOResponses;
    }

    public EmployeeDTOResponse getEmployeeByEmpId(String empId) throws EmployeeNotFoundException, DepartmentNotFoundException {

        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException("Employee " + empId + " not found in database.");
        }
        Employee employee = optionalEmployee.get();

        Optional<Department> optionalDepartment = departmentRepository.findById(employee.getDeptId());
        if (!optionalDepartment.isPresent()) {
            throw new DepartmentNotFoundException("Department " + employee.getDeptId() + " is not exist in database.");
        }
        return prepareEmployeeDTOResponse(employee, optionalDepartment.get());
    }

    public List<EmployeeDTOResponse> getEmployeeByFirstName(String firstName) throws DepartmentNotFoundException {

        List<Employee> employees = employeeRepository.findByFirstName(firstName);

        List<EmployeeDTOResponse> employeeDTOResponses = new ArrayList<>();

        for (Employee employee : employees) {
            Optional<Department> optionalDepartment = departmentRepository.findById(employee.getDeptId());
            if (!optionalDepartment.isPresent()) {
                throw new DepartmentNotFoundException(employee.getDeptId() + " is not exist in database.");
            }
            EmployeeDTOResponse employeeDTOResponse = prepareEmployeeDTOResponse(employee, optionalDepartment.get());

            employeeDTOResponses.add(employeeDTOResponse);
        }

        return employeeDTOResponses;
    }

    public List<EmployeeDTOResponse> getEmployeeByAddress(String address) throws DepartmentNotFoundException {
        List<Employee> employees = employeeRepository.findByAddress(address);

        List<EmployeeDTOResponse> employeeDTOResponses = new ArrayList<>();

        for (Employee employee : employees) {
            Optional<Department> optionalDepartment = departmentRepository.findById(employee.getDeptId());
            if (!optionalDepartment.isPresent()) {
                throw new DepartmentNotFoundException(employee.getDeptId() + " is not exist in database.");
            }
            EmployeeDTOResponse employeeDTOResponse = prepareEmployeeDTOResponse(employee, optionalDepartment.get());

            employeeDTOResponses.add(employeeDTOResponse);
        }

        return employeeDTOResponses;
    }


    public EmployeeDTOResponse updateEmployee(String empId, EmployeeDTORequest employeeDTORequest)
            throws EmployeeNotFoundException, DepartmentNotFoundException {

        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException("Employee " + empId + " not found in database.");
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(employeeDTORequest.getDeptId());
        if (!optionalDepartment.isPresent()) {
            throw new DepartmentNotFoundException("Department " + employeeDTORequest.getDeptId() + " is not exist in database.");
        }

        Employee existingEmployee = optionalEmployee.get();
        existingEmployee.setFirstName(employeeDTORequest.getFirstName());
        existingEmployee.setMiddleName(employeeDTORequest.getMiddleName());
        existingEmployee.setLastName(employeeDTORequest.getLastName());
        existingEmployee.setAddress(employeeDTORequest.getAddress());
        existingEmployee = employeeRepository.save(existingEmployee);

        return prepareEmployeeDTOResponse(existingEmployee, optionalDepartment.get());
    }

    public String updateEmployeeFirstName(String empId, String firstName) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setFirstName(firstName);
            employeeRepository.save(employee);
            return "First name is updated successfully.";
        }
        throw new EmployeeNotFoundException(empId + " not found in database.");
    }

    public String deleteEmployee(String empId) {
        employeeRepository.deleteById(empId);
        return empId + " employee deleted.";
    }

    private EmployeeDTOResponse prepareEmployeeDTOResponse(Employee employee, Department department) {
        EmployeeDTOResponse employeeDTOResponse = new EmployeeDTOResponse();
        employeeDTOResponse.setEmpId(employee.getEmpId());
        employeeDTOResponse.setFirstName(employee.getFirstName());
        employeeDTOResponse.setMiddleName(employee.getMiddleName());
        employeeDTOResponse.setLastName(employee.getLastName());
        employeeDTOResponse.setAddress(employee.getAddress());

        DepartmentDTOResponse departmentDTOResponse = new DepartmentDTOResponse();
        departmentDTOResponse.setDeptId(department.getDeptId());
        departmentDTOResponse.setDeptName(department.getDeptName());
        departmentDTOResponse.setDescription(department.getDescription());

        employeeDTOResponse.setDepartmentDTOResponse(departmentDTOResponse);

        return employeeDTOResponse;
    }
}
