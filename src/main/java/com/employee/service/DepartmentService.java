package com.employee.service;

import com.employee.dto.DepartmentDTORequest;
import com.employee.dto.DepartmentDTOResponse;
import com.employee.exception.DepartmentAlreadyExistException;
import com.employee.exception.DepartmentNotFoundException;
import java.util.List;

public interface DepartmentService {

    DepartmentDTOResponse addDepartment(DepartmentDTORequest departmentDTORequest) throws DepartmentAlreadyExistException;

    List<DepartmentDTOResponse> findAllDepartments();

    DepartmentDTOResponse getDepartmentByDeptId(String deptId) throws DepartmentNotFoundException;

    List<DepartmentDTOResponse> getDepartmentByDeptName(String deptName);

    DepartmentDTOResponse updateDepartment(String deptId, DepartmentDTORequest departmentDTORequest) throws DepartmentNotFoundException;

    String updateDepartmentName(String deptId, String deptName) throws DepartmentNotFoundException;

    String deleteDepartment(String deptId);

    List<DepartmentDTOResponse> getDeptEmpInfo();
}
