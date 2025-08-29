package com.employee.controller.role;

import com.employee.dto.role.RoleDTORequest;
import com.employee.dto.role.RoleDTOResponse;
import com.employee.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
//@PreAuthorize("hasRole('ADMIN')")  // enable if you want only ADMIN to access
public class RoleController {

    @Autowired
    private RoleService roleService;

    // ✅ Get all roles
    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        List<RoleDTOResponse> roleDTOResponses = roleService.getAccessRoles();
        return ResponseEntity.ok(roleDTOResponses);
    }

    // ✅ Create new role
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleDTORequest roleDTORequest) {
        RoleDTOResponse roleDTOResponse = roleService.createRole(roleDTORequest);
        return ResponseEntity.ok(roleDTOResponse);
    }
}

