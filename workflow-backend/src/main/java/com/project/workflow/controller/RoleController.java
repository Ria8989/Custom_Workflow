package com.project.workflow.controller;

import com.project.workflow.entity.Role;
import com.project.workflow.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/{role_name}")
    public ResponseEntity<Role> saveRole(@PathVariable("role_name") String roleName){
        try {
            return ResponseEntity.of(Optional.of(roleService.createRole(new Role(roleName))));
        }catch (Exception e){
            System.out.println("Error saving role : "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRole(){
        try {
            return ResponseEntity.of(Optional.of(roleService.readAllRole()));
        }catch (Exception e){
            System.out.println("Error getting all role : "+e);
            return ResponseEntity.status(500).build();
        }
    }

}

