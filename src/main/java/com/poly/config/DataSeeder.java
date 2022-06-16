package com.poly.config;

import com.poly.entity.Role;
import com.poly.entity.RoleName;
import com.poly.service.impl.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleServiceImp roleServiceImp;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (this.roleServiceImp.findByRoleName(RoleName.ADMIN.name()) == null) {
            Role role = new Role();
            role.setRoleName(RoleName.ADMIN.name());
            this.roleServiceImp.createRole(role);
        }
        if (this.roleServiceImp.findByRoleName(RoleName.CLIENT.name()) == null) {
            Role role = new Role();
            role.setRoleName(RoleName.CLIENT.name());
            this.roleServiceImp.createRole(role);
        }
        if (this.roleServiceImp.findByRoleName(RoleName.SHIPPER.name()) == null) {
            Role role = new Role();
            role.setRoleName(RoleName.SHIPPER.name());
            this.roleServiceImp.createRole(role);
        }

//		if (this.userServiceImp.findByUsername("laptopshop01") == null) {
//			Set<Role> role = new HashSet<>();
//			role.add(this.roleServiceImp.findByRoleName(RoleName.ADMIN.name()));
//			User user = new User();
//			user.setRoles(role);
//			user.setUsername("laptopshop01");
//			user.setFullName("lê văn hùng");
//			user.setPassword(this.bCryptPasswordEncoder.encode("123456"));
//			user.setEmail("abc@gmail.com");
//			user.setPhoneNumber("032681790");
//			user.setAddress("hà tĩnh");
//			this.userServiceImp.createUser(user);
//		}
    }
}
