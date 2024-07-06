package com.project.workflow.entity;

import com.project.workflow.utility.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

import static com.project.workflow.utility.Status.PENDING;

@Entity(name="workflow_instance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkflowInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="workflow_instance_id")
    private int workflowInstanceId;

    @ManyToOne
    @JoinColumn(name = "workflow_id", referencedColumnName = "workflow_id" )
    private Workflow workflow;

    @ManyToOne
    @JoinColumn(name = "workflow_instantiator", referencedColumnName = "user_id" )
    private User workflowInstantiator;

    @Column(name="workflow_instance_last_update")
    private Timestamp workflowInstanceLastUpdate;

    @Column(name="workflow_instance_created")
    private Timestamp workflowInstanceCreated;

    @Column(name="workflow_status")
    @Enumerated(EnumType.STRING)
    private Status workflowStatus = PENDING;

    public WorkflowInstance(Workflow workflow, User workflowInstantiator,Timestamp workflowInstanceCreated, Timestamp workflowInstanceLastUpdate) {
        this.workflow = workflow;
        this.workflowInstantiator = workflowInstantiator;
        this.workflowInstanceCreated = workflowInstanceCreated;
        this.workflowInstanceLastUpdate = workflowInstanceLastUpdate;
    }
}
