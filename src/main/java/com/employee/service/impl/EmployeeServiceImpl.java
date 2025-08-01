package com.employee.service.impl;

import com.employee.dto.EmployeeDTOPageResponse;
import com.employee.model.Department;
import com.employee.model.Employee;
import com.employee.repository.DepartmentRepository;
import com.employee.repository.EmployeeRepository;
import com.employee.dto.DepartmentDTOResponse;
import com.employee.dto.EmployeeDTORequest;
import com.employee.dto.EmployeeDTOResponse;
import com.employee.exception.DepartmentNotFoundException;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

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
        employee.setEmail(employeeDTORequest.getEmail());
        employee.setCreatedBy(employeeDTORequest.getFirstName());
        employee.setDeptId(employeeDTORequest.getDeptId());


        employee = employeeRepository.save(employee);

        return prepareEmployeeDTOResponse(employee, optionalDepartment.get());
    }

    public List<EmployeeDTOResponse> GetAllEmployees() throws DepartmentNotFoundException {
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
        existingEmployee.setEmail(employeeDTORequest.getEmail());
        existingEmployee.setDeptId(employeeDTORequest.getDeptId());
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
        employeeDTOResponse.setEmail(employee.getEmail());

        DepartmentDTOResponse departmentDTOResponse = new DepartmentDTOResponse();
        departmentDTOResponse.setDeptId(department.getDeptId());
        departmentDTOResponse.setDeptName(department.getDeptName());
        departmentDTOResponse.setDescription(department.getDescription());

        employeeDTOResponse.setDepartmentDTOResponse(departmentDTOResponse);

        return employeeDTOResponse;
    }

    public String updateEmployeeFirstAndLastName(String empId, String firstName, String lastName) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employeeRepository.save(employee);
            return "First & Last name is updated successfully.";
        }
        throw new EmployeeNotFoundException(empId + " not found in database.");
    }

    public List<String> getEmployeesFirstName() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());
    }


    public String getEmployeeByEmail(String empId) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        String email = "";
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setEmail(email);
            employeeRepository.save(employee);
            return "Email is updated Successfully. ";
        }
        throw new EmployeeNotFoundException(email + " not found in database");
    }

    @Override
    public EmployeeDTOPageResponse getEmployeeByPageAndAscByProperty(int page, int size, String property) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(property).ascending());
        Page<Employee> employees = employeeRepository.findAll(pageRequest);
        List<EmployeeDTOResponse> employeeDTOResponses = new ArrayList<>();
        EmployeeDTOPageResponse employeeDTOPageResponse = new EmployeeDTOPageResponse();

        for (Employee employee : employees) {

            EmployeeDTOResponse employeeDTOResponse = new EmployeeDTOResponse();
            employeeDTOResponse.setEmpId(employee.getEmpId());
            employeeDTOResponse.setFirstName(employee.getFirstName());
            employeeDTOResponse.setMiddleName(employee.getMiddleName());

            employeeDTOResponses.add(employeeDTOResponse);
        }
        employeeDTOPageResponse.setEmployeeDTOResponse(employeeDTOResponses);
        employeeDTOPageResponse.setPageNumber(employees.getNumber());
        employeeDTOPageResponse.setPageSize(employees.getSize());
        employeeDTOPageResponse.setTotalElement(employees.getTotalElements());

        return employeeDTOPageResponse;

    }

    @Override
    public EmployeeDTOPageResponse getEmployeeByPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Employee> employees = employeeRepository.findAll(pageRequest);
        List<EmployeeDTOResponse> employeeDTOResponses = new ArrayList<>();
        EmployeeDTOPageResponse employeeDTOPageResponse = new EmployeeDTOPageResponse();

        for (Employee employee : employees) {

            EmployeeDTOResponse employeeDTOResponse = new EmployeeDTOResponse();
            employeeDTOResponse.setEmpId(employee.getEmpId());
            employeeDTOResponse.setFirstName(employee.getFirstName());
            employeeDTOResponse.setMiddleName(employee.getMiddleName());
            employeeDTOResponse.setLastName(employee.getLastName());
            employeeDTOResponse.setEmail(employee.getEmail());
            employeeDTOResponse.setAddress(employee.getAddress());

            employeeDTOResponses.add(employeeDTOResponse);
        }

        employeeDTOPageResponse.setEmployeeDTOResponse(employeeDTOResponses);
        employeeDTOPageResponse.setPageNumber(employees.getNumber());
        employeeDTOPageResponse.setPageSize(employees.getSize());
        employeeDTOPageResponse.setTotalElement(employees.getTotalElements());

        return employeeDTOPageResponse;


    }
}

