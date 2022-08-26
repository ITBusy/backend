package com.poly.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.poly.config.JwtUtil;
import com.poly.dto.JwtRequest;
import com.poly.dto.JwtResponse;
import com.poly.dto.LoggedUser;
import com.poly.entity.Role;
import com.poly.entity.RoleName;
import com.poly.entity.User;
import com.poly.service.impl.RoleServiceImp;
import com.poly.service.impl.UserDetailsServiceImp;
import com.poly.service.impl.UserServiceImp;
import com.poly.utils.AuthenticationProvider;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

    @Autowired
    private RoleServiceImp roleServiceImp;

    @Autowired
    private UserServiceImp userServiceImp;

    @Value("${google.clientId}")
    private String CLIENT_ID;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
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

    @SneakyThrows
    @PostMapping("/google")
    public ResponseEntity<?> signInWithGoogleToken(@RequestBody String token) {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = GoogleIdToken.parse(verifier.getJsonFactory(), token);
        GoogleIdToken.Payload payload = idToken.getPayload();

        // Print user identifier
        String userId = payload.getSubject();
        User user;
        if (this.userServiceImp.findByEmail(payload.getEmail()) == null) {
            user = new User();
            user.setUsername(payload.getEmail());
            user.setEmail(payload.getEmail());
            user.setPassword(this.bCryptPasswordEncoder.encode(userId));
            user.setFullName((String) payload.get("name"));
            user.setAuthProvider(AuthenticationProvider.GOOGLE);
            user.setImageUrl((String) payload.get("picture"));
            Role role = this.roleServiceImp.findByRoleName(RoleName.CLIENT.name());
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            user.setRoles(roleSet);
            this.userServiceImp.createUser(user);
        } else {
            user = this.userServiceImp.findByEmail(payload.getEmail());
        }

        try {
            this.authenticate(user.getEmail(), user.getPassword());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        UserDetails userDetails = this.userDetailsServiceImp.loadUserByUsername(user.getUsername());
        String tokenn = this.jwtUtil.generateTokenSocial(userDetails, payload.getExpirationTimeSeconds());
        System.out.println(tokenn);
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(tokenn));
    }

    @SneakyThrows
    @PostMapping("/exists")
    public Boolean checkExists(@RequestBody Long id) {
        return this.userServiceImp.findById(id) == null;
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
