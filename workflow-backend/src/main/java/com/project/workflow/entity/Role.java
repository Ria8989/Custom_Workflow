package com.project.workflow.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name="role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private int roleId;

    @Column(name="role_name", nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<User> users;

    @ManyToMany(mappedBy = "taskroles", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Task> tasks;

    @ManyToMany(mappedBy = "workflowroles", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Workflow> workflows;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, List<User> users) {
        this.roleName = roleName;
        this.users = users;
    }
}

