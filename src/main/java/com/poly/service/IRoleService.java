package com.poly.service;

import com.poly.entity.Role;

public interface IRoleService {

    Role findByRoleName(String roleName);

    Role createRole(Role role);
}
