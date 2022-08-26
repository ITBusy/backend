package com.poly.service.impl;

import com.poly.entity.User;
import com.poly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsernameEquals(username);
        User user1 = this.userRepository.findByEmail(username);
        if (user == null && user1 == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("No user found");
        }
        return user == null ? user1 : user;
    }

}
