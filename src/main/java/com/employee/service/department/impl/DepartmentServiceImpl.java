package com.employee.service.department.impl;

import com.employee.dto.employee.EmployeeDTOResponse;
import com.employee.exception.department.DepartmentAlreadyExistException;
import com.employee.model.department.Department;
import com.employee.model.employee.Employee;
import com.employee.repository.department.DepartmentRepository;
import com.employee.dto.department.DepartmentDTORequest;
import com.employee.dto.department.DepartmentDTOResponse;
import com.employee.exception.department.DepartmentNotFoundException;
import com.employee.repository.employee.EmployeeRepository;
import com.employee.service.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
        department.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        department.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

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
            department.setUpdatedDate(LocalDateTime.now());
            department.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
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
            department.setUpdatedDate(LocalDateTime.now());
            department.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
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
    public Page<DepartmentDTOResponse> getDepartmentByPage(int pageNumber, int pageSize, String FieldName, String Direction)
            throws DepartmentNotFoundException {
        Sort.Direction direction = Direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(direction, FieldName));

        return departmentRepository.findAll(pageRequest).map(this::convertToDTO);
    }

    private DepartmentDTOResponse convertToDTO(Department department) throws DepartmentNotFoundException {


        DepartmentDTOResponse departmentDTO = new DepartmentDTOResponse();
        departmentDTO.setDeptId(department.getDeptId());
        departmentDTO.setDeptName(department.getDeptName());
        departmentDTO.setDeptName(department.getDescription());
        return departmentDTO;
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
