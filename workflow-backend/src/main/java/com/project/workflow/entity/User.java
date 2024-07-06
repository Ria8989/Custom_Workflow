package com.project.workflow.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    @Column(name="user_name", nullable = false)
    private String userName;

    @Column(name="user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name="password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="role_id", referencedColumnName = "role_id")
            }
    )
    @JsonManagedReference
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "taskusers", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Task> tasks;

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private Set<Workflow> workflow = new HashSet<>();

    public User(String userName, String userEmail, String password, Set<Role> roles) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.roles = roles;
    }
    /*
    public User(String userName, String userEmail, String password, Set<Role> roles, Set<Workflow> workflow) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.roles = roles;
        this.workflow = workflow;
    }
     */

}

