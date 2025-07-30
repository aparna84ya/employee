package com.employee.service;

import com.employee.dto.DepartmentDTOPageResponse;
import com.employee.dto.DepartmentDTORequest;
import com.employee.dto.DepartmentDTOResponse;
import com.employee.exception.DepartmentAlreadyExistException;
import com.employee.exception.DepartmentNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {

    static List<DepartmentDTOResponse> findAllDepartment() {
        return null;
    }

    DepartmentDTOResponse addDepartment(DepartmentDTORequest departmentDTORequest) throws DepartmentAlreadyExistException;

    List<DepartmentDTOResponse> findAllDepartments();

    DepartmentDTOResponse getDepartmentByDeptId(String deptId) throws DepartmentNotFoundException;

    List<DepartmentDTOResponse> getDepartmentByDeptName(String deptName);

    DepartmentDTOResponse updateDepartment(String deptId, DepartmentDTORequest departmentDTORequest) throws DepartmentNotFoundException;

    String updateDepartmentName(String deptId, String deptName) throws DepartmentNotFoundException;

    String deleteDepartment(String deptId);

    List<DepartmentDTOResponse> getDeptEmpInfo();

    DepartmentDTOPageResponse getDepartmentByPage(int page, int size);

    DepartmentDTOPageResponse getDepartmentByPageAndAscByProperty(int page, int size, String property);
}
