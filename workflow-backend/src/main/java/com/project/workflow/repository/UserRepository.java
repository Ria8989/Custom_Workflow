package com.project.workflow.repository;

import com.project.workflow.entity.Role;
import com.project.workflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserEmail(String userEmail);
    List<User> findByRoles(Role role);
}
