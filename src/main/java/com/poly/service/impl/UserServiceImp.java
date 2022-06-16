package com.poly.service.impl;

import com.poly.entity.User;
import com.poly.repository.UserRepository;
import com.poly.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsernameEquals(username);
    }

    @Override
    public User findById(Long id) throws Exception {
        return this.userRepository.findById(id).orElseThrow(() -> new Exception("Not Found with this id"));
    }

    @Override
    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            this.userRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> findAllByRole() {
        return this.userRepository.findByRoles_RoleName("SHIPPER").orElse(null);
    }
}
