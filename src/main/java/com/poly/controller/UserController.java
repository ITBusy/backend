package com.poly.controller;

import com.poly.dto.Convert;
import com.poly.dto.LoggedUser;
import com.poly.dto.ResponseObject;
import com.poly.entity.Role;
import com.poly.entity.RoleName;
import com.poly.entity.User;
import com.poly.service.impl.RoleServiceImp;
import com.poly.service.impl.UserServiceImp;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private RoleServiceImp roleServiceImp;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody LoggedUser loggedUser) {
        ResponseEntity<?> message = null;
        Set<Role> roles = new HashSet<>();
        User user = new User();
        try {
            if (loggedUser != null) {
                getDataUser(loggedUser, roles, user);
                ResponseEntity<ResponseObject> OK = this.getResponseObjectResponseEntity(user);
                if (OK != null) return OK;
                User us = this.userServiceImp.createUser(user);
                if (us != null) {
                    message = ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("OK", "Create user is success", us)
                    );

                }
            }
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", e.getMessage(), null)
            );
        }
        return message;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        List<User> list = new ArrayList<>(this.userServiceImp.getUsers());
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Not Found", "Not Found", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Found", "Found", list)
            );
        }
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable("id") Long id) {
        User user = this.userServiceImp.findById(id);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Found with this id", user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Not Found", "Not Found with this id", null));
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody LoggedUser loggedUser) {
        ResponseEntity<?> message = null;
        Set<Role> roles = new HashSet<>();
        User user = new User();
        try {
            if (loggedUser != null) {
                getDataUser(loggedUser, roles, user);
                User us = this.userServiceImp.updateUser(user);

//                ResponseEntity<ResponseObject> OK = this.getResponseObjectResponseEntity(user);
//                if (OK != null) return OK;
                if (us != null) {
                    message = ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("OK", "update user is success", us)
                    );
                }
            }
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", e.getMessage(), null)
            );
        }
        return message;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        ResponseEntity<?> mess = null;
        try {
            this.userServiceImp.deleteById(id);
        } catch (Exception e) {
            mess = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", e.getMessage(), null)
            );
        }
        return mess;
    }

    private ResponseEntity<ResponseObject> getResponseObjectResponseEntity(User user) {
        if (this.userServiceImp.findByUsername(user.getUsername()) != null && this.userServiceImp.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("EXISTS", "Username and email is already exists", null)
            );
        }
        if (this.userServiceImp.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("EXISTS", "Username is already exists", null)
            );
        }
        if (this.userServiceImp.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("EXISTS", "Email is already exists", null)
            );
        }
        return null;
    }

    private void getDataUser(@RequestBody LoggedUser loggedUser, Set<Role> roles, User user) {
        try {
            Role role;
            if (loggedUser.getAuthority() == null) {
                role = this.roleServiceImp.findByRoleName(RoleName.CLIENT.name());
            } else {
                role = this.roleServiceImp.findByRoleName(loggedUser.getAuthority());
            }
            roles.add(role);
            user.setRoles(roles);
            if (loggedUser.getImageUrl() == null) {
                user.setImageUrl("https://firebasestorage.googleapis.com/v0/b/asmjava5.appspot.com/o/Avatar%2Ftlsqp7hcf6?alt=media&token=e01dc2e6-ac50-4090-b510-cc889ed12b42");
            } else {
                user.setImageUrl(loggedUser.getImageUrl());
            }
            user.setId(loggedUser.getId());
            user.setPassword(this.bCryptPasswordEncoder.encode(loggedUser.getPassword()));
            user.setFullName(Convert.CapitalAllFirstLetter(loggedUser.getFullName()));
            user.setAddress(Convert.CapitalAllFirstLetter(loggedUser.getAddress()));
            user.setPhoneNumber(loggedUser.getPhoneNumber());
            user.setUsername(loggedUser.getUsername());
            user.setEmail(loggedUser.getEmail());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/role-name")
    public ResponseEntity<ResponseObject> findAllByRoleName() {
        return ResponseEntity.ok(new ResponseObject("Ok", "Have data", this.userServiceImp.findAllByRole()));
    }
}
