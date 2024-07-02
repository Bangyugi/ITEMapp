package com.bangvan.EMwebapp.repository;

import com.bangvan.EMwebapp.dto.response.UserResponse;
import com.bangvan.EMwebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findAllByUsername(String username);

    User findByUsername(String username);

    User findByEmail(String email);
}
