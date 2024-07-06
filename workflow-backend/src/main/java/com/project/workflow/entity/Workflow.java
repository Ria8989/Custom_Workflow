package com.project.workflow.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name="workflow")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="workflow_id")
    private int workflowId;

    @Column(name="workflow_name", nullable = false)
    private String workflowName;

    @Column(name="workflow_description", length = 1000)
    private String workflowDescription;

    @ManyToOne
    @JoinColumn(name = "workflow_creator", referencedColumnName = "user_id" )
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "workflow_role",
            joinColumns = {
                    @JoinColumn(name = "workflow_id", referencedColumnName = "workflow_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="role_id", referencedColumnName = "role_id")
            }
    )
    @JsonManagedReference
    private Set<Role> workflowroles = new HashSet<>();

    @Column(name="all_role")
    private Boolean isAllRole = false;

    public Workflow(String workflowName, String workflowDescription, User creator) {
        this.workflowName = workflowName;
        this.workflowDescription = workflowDescription;
        this.creator = creator;
    }

    public Workflow(String workflowName, String workflowDescription, User creator, Boolean isAllRole) {
        this.workflowName = workflowName;
        this.workflowDescription = workflowDescription;
        this.creator = creator;
        this.isAllRole = isAllRole;
    }

    public void addRole(Role role){
        this.workflowroles.add(role);
    }
}

