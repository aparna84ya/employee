package com.employee.service.role;

import com.employee.dto.role.RoleDTORequest;
import com.employee.dto.role.RoleDTOResponse;

import java.util.List;

public interface RoleService {
    RoleDTOResponse createRole(RoleDTORequest roleDTORequest);
    List<RoleDTOResponse> getAccessRoles();
}
