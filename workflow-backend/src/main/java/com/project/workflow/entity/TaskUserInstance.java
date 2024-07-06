package com.project.workflow.entity;

import com.project.workflow.utility.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import static com.project.workflow.utility.Status.PENDING;

@Entity(name="task_user_instance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskUserInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_user_instance_id")
    private int taskUserInstanceId;

    @ManyToOne
    @JoinColumn(name = "task_instance_id", referencedColumnName = "task_instance_id" )
    private TaskInstance taskInstance;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id" )
    private User user;

    @Column(name="comment", length = 1000)
    private String comment;

    @Column(name="task_user_instance_last_update")
    private Timestamp taskUserInstanceLastUpdate;

    @Column(name="task_user_instance_created")
    private Timestamp taskUserInstanceCreated;

    @Column(name="upload_file_path")
    private String uploadFilePath;

    @Column(name="eligible_action")
    private String eligibleAction;

    @Column(name="action_status")
    private String actionStatus;

    @Column(name="task_user_instance_status")
    @Enumerated(EnumType.STRING)
    private Status taskUserStatus = PENDING;

    public TaskUserInstance(TaskInstance taskInstance, User user, String comment, Timestamp taskUserInstanceLastUpdate, String uploadFilePath) {
        this.taskInstance = taskInstance;
        this.user = user;
        this.comment = comment;
        this.taskUserInstanceLastUpdate = taskUserInstanceLastUpdate;
        this.uploadFilePath = uploadFilePath;
    }

    public TaskUserInstance(TaskInstance taskInstance, User user, String eligibleAction, Timestamp taskUserInstanceCreated, Timestamp taskUserInstanceLastUpdate) {
        this.taskInstance = taskInstance;
        this.user = user;
        this.eligibleAction = eligibleAction;
        this.taskUserInstanceCreated = taskUserInstanceCreated;
        this.taskUserInstanceLastUpdate = taskUserInstanceLastUpdate;
    }
}

