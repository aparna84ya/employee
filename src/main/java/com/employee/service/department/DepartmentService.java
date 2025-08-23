package com.employee.service.department;

import com.employee.dto.department.DepartmentDTOPageResponse;
import com.employee.dto.department.DepartmentDTORequest;
import com.employee.dto.department.DepartmentDTOResponse;
import com.employee.exception.department.DepartmentAlreadyExistException;
import com.employee.exception.department.DepartmentNotFoundException;
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

   Page<DepartmentDTOResponse> getDepartmentByPage(int pageNumber, int pageSize, String sortFieldName, String sortDirection);
}
