package com.project.workflow.service;

import com.project.workflow.entity.Role;
import com.project.workflow.entity.User;
import com.project.workflow.model.UserInput;
import com.project.workflow.repository.RoleRepository;
import com.project.workflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public User createUser(UserInput user){
        System.out.println("We reached here");
        Set<Role> rSet = new HashSet<Role>();
        for (String name : user.getRoles())
            rSet.add(roleRepository.findByRoleName(name));
        User newUser = new User(user.getUserName(), user.getUserEmail(), user.getPassword(), rSet);
        return userRepository.save(newUser);
    }

    public User readUserByEmail(String email){
        return userRepository.findByUserEmail(email);
    }

    public List<User> readAllUsers(){
        return userRepository.findAll();
    }

}
