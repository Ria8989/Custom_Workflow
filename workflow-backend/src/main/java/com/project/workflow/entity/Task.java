package com.project.workflow.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.project.workflow.utility.TaskUserType;
import com.project.workflow.utility.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity(name="task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task implements Comparable<Task>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private int taskId;

    @Column(name="task_name", nullable = false)
    private String taskName;

    @Column(name="task_description")
    private String taskDescription;

    @Column(name="task_type")
    @Enumerated(EnumType.STRING)
    private Type taskType = Type.MANDATORY;

    @ManyToOne
    @JoinColumn(name = "workflow_id", referencedColumnName = "workflow_id" )
    private Workflow workflow;

    @Column(name="task_user_type")
    @Enumerated(EnumType.STRING)
    private TaskUserType taskUserType;

    @Column(name="enable_upload")
    private Boolean enableUpload;

    @Column(name="upload_type")
    @Enumerated(EnumType.STRING)
    private Type uploadType;

    @Column(name="eligible_action", length = 500)
    private String eligibleAction;

    @Column(name="is_first_task")
    private Boolean isFirstTask = false;

    @Column(name="all_user")
    private Boolean allUser = true;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "task_user",
            joinColumns = {
                    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="user_id", referencedColumnName = "user_id")
            }
    )
    @JsonManagedReference
    private Set<User> taskusers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "task_role",
            joinColumns = {
                    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="role_id", referencedColumnName = "role_id")
            }
    )
    @JsonManagedReference
    private Set<Role> taskroles= new HashSet<>();


    public void addUsers(User user){
        this.taskusers.add(user);
    }

    public void addRoles(Role role){
        this.taskroles.add(role);
    }

    public Task(String taskName, String taskDescription, Type taskType, Workflow workflow, TaskUserType taskUserType, Boolean enableUpload, Type uploadType, Boolean allUser) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskType = taskType;
        this.workflow = workflow;
        this.taskUserType = taskUserType;
        this.enableUpload = enableUpload;
        this.uploadType = uploadType;
        this.allUser = allUser;
    }

    public int getTaskId() {
        return taskId;
    }

    @Override
    public int compareTo(Task t) {
        return taskId<t.getTaskId()?-1:taskId>t.getTaskId()?1:0;
    }
}

