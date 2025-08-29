package com.employee.service.role.impl;

import com.employee.dto.role.RoleDTORequest;
import com.employee.dto.role.RoleDTOResponse;
import com.employee.model.role.Role;
import com.employee.repository.role.RoleRepository;
import com.employee.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository; // MongoRepository<Role, String>

    @Override
    public RoleDTOResponse createRole(RoleDTORequest roleDTORequest) {
        Role role = new Role();
        role.setRoleId(UUID.randomUUID().toString().split("-")[0]);
        role.setRole(roleDTORequest.getRole().toUpperCase());
        role.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        role.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        role.setEnabled(true);

        role = roleRepository.save(role);

        return prepareRoleDTOResponse(role);
    }

    @Override
    public List<RoleDTOResponse> getAccessRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(this::prepareRoleDTOResponse)
                .collect(Collectors.toList());
    }

    private RoleDTOResponse prepareRoleDTOResponse(Role role) {
        RoleDTOResponse roleDTOResponse = new RoleDTOResponse();
        roleDTOResponse.setRoleId(role.getRoleId());
        roleDTOResponse.setRole(role.getRole());

        return roleDTOResponse;
    }
}
