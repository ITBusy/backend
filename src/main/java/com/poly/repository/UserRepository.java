package com.poly.repository;

import com.poly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameEquals(String username);

    User findByEmail(String email);

    Optional<List<User>> findByRoles_RoleName(String roleName);
}