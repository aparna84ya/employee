//package com.employee.service;
//
//import com.employee.dto.employee.EmployeeDTORequest;
//import com.employee.dto.employee.EmployeeDTOResponse;
//import com.employee.exception.department.DepartmentNotFoundException;
//import com.employee.model.department.Department;
//import com.employee.model.employee.Employee;
//import com.employee.repository.department.DepartmentRepository;
//import com.employee.repository.employee.EmployeeRepository;
//import com.employee.service.employee.impl.EmployeeServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//
//@ExtendWith(MockitoExtension.class)
//public class EmployeeService1Test {
//    @InjectMocks
//    private EmployeeServiceImpl employeeService;
//
//    @Mock
//    private EmployeeRepository employeeRepository;
//
//    @Mock
//    private DepartmentRepository departmentRepository;
//
//    @Test
//    void AddEmployee_Success() {
//        // Arrange
//        Employee employee = new Employee();
//        String uuid = UUID.randomUUID().toString().split("-")[0];
//        employee.setEmpId(uuid);
//        employee.setFirstName("Aparna");
//        employee.setMiddleName("kr");
//        employee.setLastName("Chaurasia");
//        employee.setAddress("Noida");
//        employee.setEmail("Chaurasia@gmail.com");
//        employee.setDeptId("abc23");
//
//        EmployeeDTORequest employeeDTORequest = new EmployeeDTORequest();
//        employeeDTORequest.setFirstName("Aparna");
//        employeeDTORequest.setMiddleName("kr");
//        employeeDTORequest.setLastName("Chaurasia");
//        employeeDTORequest.setAddress("Noida");
//        employeeDTORequest.setEmail("Chaurasia@gmail.com");
//        employeeDTORequest.setDeptId("abc23");
//
//        // Mock
//        Mockito.when(departmentRepository.findById(anyString())).thenReturn(Optional.of(new Department()));
//        Mockito.when(employeeRepository.save(any())).thenReturn(employee);
//
//        // Invoke
//        EmployeeDTOResponse result = employeeService.addEmployee(employeeDTORequest);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(uuid, result.getEmpId());
//        Assertions.assertEquals(employeeDTORequest.getFirstName(), result.getFirstName());
//        Assertions.assertEquals(employeeDTORequest.getMiddleName(), result.getMiddleName());
//        Assertions.assertEquals(employeeDTORequest.getLastName(), result.getLastName());
//        Assertions.assertEquals(employeeDTORequest.getAddress(), result.getAddress());
//        Assertions.assertEquals(employeeDTORequest.getEmail(), result.getEmail());
//    }
//
//    @Test
//    void AddEmployee_Dept_Id_Is_Null_Or_Empty() {
//        // Invoke
//        DepartmentNotFoundException exception = Assertions.assertThrows(DepartmentNotFoundException.class, () -> {
//            employeeService.addEmployee(new EmployeeDTORequest());
//        });
//
//        // Assert
//        Assertions.assertNotNull(exception);
//        Assertions.assertEquals("Dept id is empty.", exception.getMessage());
//
//    }
//
//    @Test
//    void AddEmployee_Dept_Not_Exist() {
//        // Arrange
//        EmployeeDTORequest employeeDTORequest = new EmployeeDTORequest();
//        employeeDTORequest.setDeptId("deptId");
//
//        // Mock
//        Mockito.when(departmentRepository.findById(anyString())).thenReturn(Optional.empty());
//
//        // Invoke
//        DepartmentNotFoundException exception = Assertions.assertThrows(DepartmentNotFoundException.class, () -> {
//            employeeService.addEmployee(employeeDTORequest);
//        });
//
//        // Assert
//        Assertions.assertNotNull(exception);
//        Assertions.assertEquals(employeeDTORequest.getDeptId() +
//                " is not exist in database.", exception.getMessage());
//
//    }
//
//    @Test
//    void AddEmployee_Null_Pointer_Exception() {
//        // Arrange
//        EmployeeDTORequest employeeDTORequest = new EmployeeDTORequest();
//        employeeDTORequest.setDeptId("abc23");
//
//        // Mock
//        Mockito.when(departmentRepository.findById(anyString())).thenReturn(Optional.of(new Department()));
//
//        // Invoke
//        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
//            employeeService.addEmployee(employeeDTORequest);
//        });
//
//        Assertions.assertNotNull(exception);
//    }
//
//}
