package com.employee.service.impl;

import com.employee.dto.DepartmentDTOPageResponse;
import com.employee.dto.EmployeeDTOResponse;
import com.employee.exception.DepartmentAlreadyExistException;
import com.employee.model.Department;
import com.employee.model.Employee;
import com.employee.repository.DepartmentRepository;
import com.employee.dto.DepartmentDTORequest;
import com.employee.dto.DepartmentDTOResponse;
import com.employee.exception.DepartmentNotFoundException;
import com.employee.repository.EmployeeRepository;
import com.employee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public DepartmentDTOResponse addDepartment(DepartmentDTORequest departmentDTORequest) throws DepartmentAlreadyExistException {
        List<Department> departments = departmentRepository.findByDeptName(departmentDTORequest.getDeptName());
        if(Objects.nonNull(departments) && !departments.isEmpty()) {
            throw new DepartmentAlreadyExistException("Department already exist by " + departmentDTORequest.getDeptName() + " name.");
        }

        Department department = new Department();
        department.setDeptId(UUID.randomUUID().toString().split("-")[0]);
        department.setDeptName(departmentDTORequest.getDeptName());
        department.setDescription(departmentDTORequest.getDescription());
        department.setCreatedDate(LocalDateTime.now());
        department.setUpdatedDate(LocalDateTime.now());
        department.setCreatedBy("test");

        department = departmentRepository.save(department);

        DepartmentDTOResponse departmentDTOResponse = new DepartmentDTOResponse();
        departmentDTOResponse.setDeptId(department.getDeptId());
        departmentDTOResponse.setDeptName(department.getDeptName());
        departmentDTOResponse.setDescription(department.getDescription());

        return departmentDTOResponse;
    }

    public List<DepartmentDTOResponse> findAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTOResponse> departmentDTOResponses = new ArrayList<>();

        for(Department department : departments) {
            DepartmentDTOResponse departmentDTOResponse = new DepartmentDTOResponse();
            departmentDTOResponse.setDeptId(department.getDeptId());
            departmentDTOResponse.setDeptName(department.getDeptName());
            departmentDTOResponse.setDescription(department.getDescription());

            departmentDTOResponses.add(departmentDTOResponse);
        }

        return departmentDTOResponses;
    }

    public DepartmentDTOResponse getDepartmentByDeptId(String deptId) throws DepartmentNotFoundException {
        Optional<Department> optionalDepartment = departmentRepository.findById(deptId);

        if(optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();

            DepartmentDTOResponse departmentDTOResponse = new DepartmentDTOResponse();
            departmentDTOResponse.setDeptId(department.getDeptId());
            departmentDTOResponse.setDeptName(department.getDeptName());
            departmentDTOResponse.setDescription(department.getDescription());

            return departmentDTOResponse;
        }
        throw new DepartmentNotFoundException(deptId + " is not found in database.");
    }

    public List<DepartmentDTOResponse> getDepartmentByDeptName(String deptName) {
        List<Department> departments = departmentRepository.findByDeptName(deptName);
        List<DepartmentDTOResponse> departmentDTOResponses = new ArrayList<>();

        for(Department department : departments) {
            DepartmentDTOResponse departmentDTOResponse = new DepartmentDTOResponse();
            departmentDTOResponse.setDeptId(department.getDeptId());
            departmentDTOResponse.setDeptName(department.getDeptName());
            departmentDTOResponse.setDescription(department.getDescription());

            departmentDTOResponses.add(departmentDTOResponse);
        }

        return departmentDTOResponses;
    }

    public DepartmentDTOResponse updateDepartment(String deptId, DepartmentDTORequest departmentDTORequest) throws DepartmentNotFoundException {
        Optional<Department> optionalDepartment = departmentRepository.findById(deptId);

        if(optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            department.setDeptName(departmentDTORequest.getDeptName());
            department.setDescription(departmentDTORequest.getDescription());
            department = departmentRepository.save(department);

            DepartmentDTOResponse departmentDTOResponse = new DepartmentDTOResponse();
            departmentDTOResponse.setDeptId(department.getDeptId());
            departmentDTOResponse.setDeptName(department.getDeptName());
            departmentDTOResponse.setDescription(department.getDescription());

            return departmentDTOResponse;
        }
        throw new DepartmentNotFoundException(deptId + " is not found in database.");
    }

    public String updateDepartmentName(String deptId, String deptName) throws DepartmentNotFoundException {
        Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
        if(optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            department.setDeptName(deptName);
            departmentRepository.save(department);
            return "Department name is updated successfully.";
        }
        throw new DepartmentNotFoundException(deptId + " not found in database.");
    }

    public String deleteDepartment(String deptId) {
        departmentRepository.deleteById(deptId);
        return deptId + " employee deleted.";
    }

    public List<DepartmentDTOResponse> getDeptEmpInfo() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTOResponse> departmentDTOResponses = new ArrayList<>();

        for(Department department : departments) {

            DepartmentDTOResponse departmentDTOResponse = new DepartmentDTOResponse();
            departmentDTOResponse.setDeptId(department.getDeptId());
            departmentDTOResponse.setDeptName(department.getDeptName());
            departmentDTOResponse.setDescription(department.getDescription());

            List<Employee> employees = employeeRepository.findByDeptId(department.getDeptId());
            List<EmployeeDTOResponse> employeeDTOResponses = getEmployeeDTOResponses(employees);
            departmentDTOResponse.setEmployeeDTOResponses(employeeDTOResponses);
            departmentDTOResponses.add(departmentDTOResponse);
        }
        return departmentDTOResponses;
    }

    @Override
    public DepartmentDTOPageResponse getDepartmentByPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Department> departments = departmentRepository.findAll(pageRequest);
        List<DepartmentDTOResponse> departmentDTOResponses = new ArrayList<>();
        DepartmentDTOPageResponse departmentDTOPageResponse = new DepartmentDTOPageResponse();

        for (Department department : departments) {

            DepartmentDTOResponse departmentDTOResponse = new DepartmentDTOResponse();
            departmentDTOResponse.setDeptId(department.getDeptId());
            departmentDTOResponse.setDeptName(department.getDeptName());
            departmentDTOResponse.setDescription(department.getDescription());

            departmentDTOResponses.add(departmentDTOResponse);
        }
        departmentDTOPageResponse.setDepartmentDTOResponses(departmentDTOResponses);
        departmentDTOPageResponse.setPageNumber(departments.getNumber());
        departmentDTOPageResponse.setPageSize(departments.getSize());
        departmentDTOPageResponse.setTotalElement(departments.getTotalElements());

        return departmentDTOPageResponse;
    }

    @Override
    public DepartmentDTOPageResponse getDepartmentByPageAndAscByProperty(int page, int size, String property) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(property).ascending());
        Page<Department> departments = departmentRepository.findAll(pageRequest);
        List<DepartmentDTOResponse> departmentDTOResponses = new ArrayList<>();
        DepartmentDTOPageResponse departmentDTOPageResponse = new DepartmentDTOPageResponse();

        for(Department department : departments) {

            DepartmentDTOResponse departmentDTOResponse = new DepartmentDTOResponse();
            departmentDTOResponse.setDeptId(department.getDeptId());
            departmentDTOResponse.setDeptName(department.getDeptName());
            departmentDTOResponse.setDescription(department.getDescription());

            departmentDTOResponses.add(departmentDTOResponse);
        }
        departmentDTOPageResponse.setDepartmentDTOResponses(departmentDTOResponses);
        departmentDTOPageResponse.setPageNumber(departments.getNumber());
        departmentDTOPageResponse.setPageSize(departments.getSize());
        departmentDTOPageResponse.setTotalElement(departments.getTotalElements());

        return  departmentDTOPageResponse;
    }

    private static List<EmployeeDTOResponse> getEmployeeDTOResponses(List<Employee> employees) {
        List<EmployeeDTOResponse> employeeDTOResponses = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeDTOResponse employeeDTOResponse = new EmployeeDTOResponse();
            employeeDTOResponse.setEmpId(employee.getEmpId());
            employeeDTOResponse.setFirstName(employee.getFirstName());
            employeeDTOResponse.setMiddleName(employee.getMiddleName());
            employeeDTOResponse.setLastName(employee.getLastName());
            employeeDTOResponse.setAddress(employee.getAddress());
            employeeDTOResponse.setEmail(employee.getEmail());


            employeeDTOResponses.add(employeeDTOResponse);
        }
        return employeeDTOResponses;
    }
}
