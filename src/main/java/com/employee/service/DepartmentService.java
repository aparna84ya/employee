package com.employee.service;

import com.employee.model.Department;
import com.employee.repository.DepartmentRepository;
import com.employee.dto.DepartmentDTORequest;
import com.employee.dto.DepartmentDTOResponse;
import com.employee.exception.DepartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentDTOResponse addDepartment(DepartmentDTORequest departmentDTORequest) {
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

    public DepartmentDTOResponse updateDepartment(DepartmentDTORequest departmentDTORequest) throws DepartmentNotFoundException {
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentDTORequest.getDeptId());

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
        throw new DepartmentNotFoundException(departmentDTORequest.getDeptId() + " is not found in database.");
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

    public ResponseEntity<?> getDeptEmpInfo() {

    }
}
