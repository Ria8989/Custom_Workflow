package com.project.workflow.entity;

import com.project.workflow.utility.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

import static com.project.workflow.utility.Status.PENDING;

@Entity(name="task_instance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_instance_id")
    private int taskInstanceId;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "task_id" )
    private Task task;

    @ManyToOne
    @JoinColumn(name = "workflow_instance_id", referencedColumnName = "workflow_instance_id" )
    private WorkflowInstance workflowInstance;

    @Column(name="is_first_task_instance")
    private Boolean isFirstTaskInstance = false;

    @Column(name="task_instance_last_update")
    private Timestamp taskInstanceLastUpdate;

    @Column(name="task_instance_created")
    private Timestamp taskInstanceCreated;

    @Column(name="task_status")
    @Enumerated(EnumType.STRING)
    private Status taskStatus = PENDING;

    public TaskInstance(Task task, WorkflowInstance workflowInstance, Timestamp taskInstanceLastUpdate) {
        this.task = task;
        this.workflowInstance = workflowInstance;
        this.taskInstanceLastUpdate = taskInstanceLastUpdate;
    }

    public TaskInstance(Task task, WorkflowInstance workflowInstance, Timestamp taskInstanceCreated, Timestamp taskInstanceLastUpdate, Boolean isFirstTaskInstance) {
        this.task = task;
        this.workflowInstance = workflowInstance;
        this.taskInstanceCreated = taskInstanceCreated;
        this.taskInstanceLastUpdate = taskInstanceLastUpdate;
        this.isFirstTaskInstance= isFirstTaskInstance;
    }
}
