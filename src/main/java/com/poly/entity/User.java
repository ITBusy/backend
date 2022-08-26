package com.poly.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.poly.config.CustomAuthorityDeserializer;
import com.poly.dto.Authority;
import com.poly.utils.AuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String username;

    private String password;

    private String fullName;

    @NotBlank
    private String email;

    @Column(name = "phoneNumber", length = 10)
    private String phoneNumber;

    private String address;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "userOrder")
    @JsonIgnore
    private Set<Order> orderSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "shipperUser")
    @JsonIgnore
    private Set<ShipperOrder> shipperOrdersSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Comment> commentSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userReply")
    @JsonIgnore
    private Set<CommentDetails> commentDetailsSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<CommentDetails> commentDetailsSetUser = new LinkedHashSet<>();

    public User() {
    }

    public User(Long id, String username, String password, String fullName, String email,
                String phoneNumber, String address, String imageUrl, AuthenticationProvider authProvider) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.imageUrl = imageUrl;
        this.authProvider = authProvider;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> auths = new HashSet<>();
        this.roles.forEach(e -> {
            auths.add(new Authority(e.getRoleName()));
        });
        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
    }

    public Set<CommentDetails> getCommentDetailsSet() {
        return commentDetailsSet;
    }

    public void setCommentDetailsSet(Set<CommentDetails> commentDetailsSet) {
        this.commentDetailsSet = commentDetailsSet;
    }

    public Set<ShipperOrder> getShipperOrdersSet() {
        return shipperOrdersSet;
    }

    public void setShipperOrdersSet(Set<ShipperOrder> shipperOrdersSet) {
        this.shipperOrdersSet = shipperOrdersSet;
    }

    public Set<CommentDetails> getCommentDetailsSetUser() {
        return commentDetailsSetUser;
    }

    public void setCommentDetailsSetUser(Set<CommentDetails> commentDetailsSetUser) {
        this.commentDetailsSetUser = commentDetailsSetUser;
    }

    public AuthenticationProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }
}
