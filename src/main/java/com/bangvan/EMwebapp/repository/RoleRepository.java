package com.bangvan.EMwebapp.repository;

import com.bangvan.EMwebapp.dto.response.RoleResponse;
import com.bangvan.EMwebapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String roleEmployee);
}
