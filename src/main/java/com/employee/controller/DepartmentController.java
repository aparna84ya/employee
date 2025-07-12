package com.employee.controller;


import com.employee.service.DepartmentService;
import com.employee.dto.DepartmentDTORequest;
import com.employee.dto.DepartmentDTOResponse;
import com.employee.exception.DepartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    //Inject the Dependency
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDTOResponse createDepartment(@RequestBody DepartmentDTORequest departmentDTORequest){
        return departmentService.addDepartment(departmentDTORequest);
    }
    @GetMapping
    public ResponseEntity<List<DepartmentDTOResponse>> getDepartments() {
        return ResponseEntity.ok(departmentService.findAllDepartments());
    }

    @GetMapping("/deptEmpInfo")
    public ResponseEntity<?> getDeptEmpInfo() {
        return departmentService.getDeptEmpInfo();
    }


    @GetMapping("/{deptId}")
    public ResponseEntity<?> getDepartment(@PathVariable String deptId){
        try {
            return ResponseEntity.ok(departmentService.getDepartmentByDeptId(deptId));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/departmentName/{deptName}")
    public List<DepartmentDTOResponse> findDepartmentUsingFirstName(@PathVariable String deptName){
        return departmentService.getDepartmentByDeptName(deptName);
    }

    @PutMapping
    public ResponseEntity<?> updateDepartment(@RequestBody DepartmentDTORequest departmentDTORequest){
        try {
            return ResponseEntity.ok(departmentService.updateDepartment(departmentDTORequest));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{deptId}/{deptName}")
    public ResponseEntity<?> updateDepartmentDeptName(@PathVariable String deptId, @PathVariable String deptName)
            throws DepartmentNotFoundException {
        try{
            String responseMessage = departmentService.updateDepartmentName(deptId, deptName);
            return ResponseEntity.ok(responseMessage);
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{deptId}")
    public String deleteDepartment(@PathVariable String deptId){
        return departmentService.deleteDepartment(deptId);
    }
}
