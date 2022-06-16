package com.poly.service;

import com.poly.entity.User;

import java.util.List;

public interface IUserService {

    List<User> getUsers();

    User createUser(User user);

    User findByUsername(String username);

    User findById(Long id) throws Exception;

    User updateUser(User user);

    User findByEmail(String email);

    boolean deleteById(Long id);

    List<User> findAllByRole();
}
