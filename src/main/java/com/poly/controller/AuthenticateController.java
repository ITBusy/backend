package com.poly.controller;

import com.poly.config.JwtUtil;
import com.poly.dto.JwtRequest;
import com.poly.dto.JwtResponse;
import com.poly.dto.LoggedUser;
import com.poly.entity.User;
import com.poly.service.impl.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        ResponseEntity<?> message;
        try {
            this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Username not found " + e.getMessage());
        }
        UserDetails userDetails = this.userDetailsServiceImp.loadUserByUsername(jwtRequest.getUsername());
        if (this.bCryptPasswordEncoder.matches(jwtRequest.getPassword(), userDetails.getPassword())) {
            String token = this.jwtUtil.generateToken(userDetails);
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password incorrect");
        }
    }

    public void authenticate(String username, String password) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            System.out.println("User disable " + e.getMessage());
        } catch (BadCredentialsException e) {
            System.out.println("User credential " + e.getMessage());
        }
    }

    @GetMapping("/current-user")
    public LoggedUser getCurrentUser(Principal principal) {
        User user = (User) this.userDetailsServiceImp.loadUserByUsername(principal.getName());
        return new LoggedUser(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getPassword(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getImageUrl(),
                user.getAuthorities().stream().iterator().next().getAuthority()
        );
    }
}
