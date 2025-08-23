package com.employee.controller.department;

import com.employee.exception.department.DepartmentAlreadyExistException;
import com.employee.service.department.DepartmentService;
import com.employee.dto.department.DepartmentDTORequest;
import com.employee.dto.department.DepartmentDTOResponse;
import com.employee.exception.department.DepartmentNotFoundException;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
@CrossOrigin
public class DepartmentController {
    //Inject the Dependency
    @Autowired
    private DepartmentService departmentService;
    @GetMapping
    public ResponseEntity<?> getDepartment(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "4") int pageSize,
            @RequestParam(defaultValue = "Department Name") String FieldName,
            @RequestParam(required = false, defaultValue = "ASC") String Direction) {
        try {
            if (pageNumber < 0 || pageSize <= 0) {
                return ResponseEntity.badRequest()
                        .body("Page number must be >= 0 and page size must be > 0");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(departmentService.getDepartmentByPage(pageNumber, pageSize, FieldName, Direction));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentDTORequest departmentDTORequest){
        try {
            DepartmentDTOResponse departmentDTOResponse = departmentService.addDepartment(departmentDTORequest);
            return ResponseEntity.ok(departmentDTOResponse);
        } catch (DepartmentAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
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

    @PutMapping("/{deptId}")
    public ResponseEntity<?> updateDepartment(@PathVariable String deptId, @RequestBody DepartmentDTORequest departmentDTORequest){
        try {
            return ResponseEntity.ok(departmentService.updateDepartment(deptId, departmentDTORequest));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{deptId}/{deptName}")
    public ResponseEntity<?> updateDepartmentName(@PathVariable String deptId, @PathVariable String deptName)
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

    @GetMapping("/departmentName/{deptName}")
    public List<DepartmentDTOResponse> getDepartmentUsingDeptName(@PathVariable String deptName){
        return departmentService.getDepartmentByDeptName(deptName);
    }

    @GetMapping("/deptEmpInfo")
    public List<DepartmentDTOResponse> getDeptEmpInfo() {
        return departmentService.getDeptEmpInfo();
    }


}


