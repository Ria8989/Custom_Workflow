package com.project.workflow.service;

import com.project.workflow.entity.Role;
import com.project.workflow.entity.User;
import com.project.workflow.repository.RoleRepository;
import com.project.workflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    public Role createRole(Role role){
        Role result = roleRepository.save(role);
        return result;
    }

    public List<Role> readAllRole(){
        return roleRepository.findAll();
    }

    public Set<User> readUserOnRole(Set<Role> roles){
        Set<User> user= new HashSet<User>();

        for(Role role: roles){
            user.addAll(userRepository.findByRoles(role));
        }
        return user;
    }

}
