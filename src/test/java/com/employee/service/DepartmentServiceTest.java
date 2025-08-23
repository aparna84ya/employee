package com.employee.service;

import com.employee.dto.department.DepartmentDTORequest;
import com.employee.dto.department.DepartmentDTOResponse;
import com.employee.exception.department.DepartmentAlreadyExistException;
import com.employee.model.department.Department;
import com.employee.repository.department.DepartmentRepository;
import com.employee.service.department.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Test
     void AddDepartment_Success() throws DepartmentAlreadyExistException {
        Department department = new Department();
        String uuid = UUID.randomUUID().toString().split("-")[0];
        department.setDeptId(uuid);
        department.setDeptName("Economics");
        department.setDescription("Deals with financial sector.");
        department.setCreatedBy("test");

        DepartmentDTORequest departmentDTORequest = new DepartmentDTORequest();
        departmentDTORequest.setDeptName("Economics");

        Mockito.when(departmentRepository.findByDeptName(anyString())).thenReturn(new ArrayList<>());
        Mockito.when(departmentRepository.save(any())).thenReturn(department);

        DepartmentDTOResponse result = departmentService.addDepartment(departmentDTORequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(uuid, result.getDeptId());
        Assertions.assertEquals("Economics", result.getDeptName());
        Assertions.assertEquals("Deals with financial sector.", result.getDescription());
    }

    @Test
    void AddDepartment_Exist_Error() {
        DepartmentDTORequest departmentDTORequest = new DepartmentDTORequest();
        departmentDTORequest.setDeptName("Economics");

        List<Department> departments = new ArrayList<>();
        departments.add(new Department());

        Mockito.when(departmentRepository.findByDeptName(anyString())).thenReturn(departments);

        DepartmentAlreadyExistException exception = Assertions.assertThrows(DepartmentAlreadyExistException.class, () -> {
            departmentService.addDepartment(departmentDTORequest);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Department already exist by Economics name.", exception.getMessage());
    }

    @Test
    void AddDepartment_Null_Pointer_Exception() {
        DepartmentDTORequest departmentDTORequest = new DepartmentDTORequest();
        departmentDTORequest.setDeptName("Economics");

        Mockito.when(departmentRepository.findByDeptName(anyString())).thenReturn(null);

        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            departmentService.addDepartment(departmentDTORequest);
        });

        Assertions.assertNotNull(exception);
    }
}
