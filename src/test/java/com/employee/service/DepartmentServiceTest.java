package com.employee.service;

import com.employee.dto.DepartmentDTORequest;
import com.employee.dto.DepartmentDTOResponse;
import com.employee.exception.DepartmentAlreadyExistException;
import com.employee.model.Department;
import com.employee.repository.DepartmentRepository;
import com.employee.service.impl.DepartmentServiceImpl;
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
     void testAddDepartment() throws DepartmentAlreadyExistException {
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
    void testDepartmentAlreadyExistException() {
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
    void testAddDepartmentNUllPointerException() {
        DepartmentDTORequest departmentDTORequest = new DepartmentDTORequest();
        departmentDTORequest.setDeptName("Economics");

        Mockito.when(departmentRepository.findByDeptName(anyString())).thenReturn(null);

        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            departmentService.addDepartment(departmentDTORequest);
        });

        Assertions.assertNotNull(exception);
    }
}
