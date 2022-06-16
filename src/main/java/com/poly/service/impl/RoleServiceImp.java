package com.poly.service.impl;

import com.poly.entity.Role;
import com.poly.repository.RoleRepository;
import com.poly.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(String roleName) {
        return this.roleRepository.findByRoleName(roleName);
    }

    @Override
    public Role createRole(Role role) {
        return this.roleRepository.save(role);
    }
}
