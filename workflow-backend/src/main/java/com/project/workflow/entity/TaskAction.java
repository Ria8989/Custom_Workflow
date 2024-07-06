package com.project.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="task_action")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_action_id")
    private int taskActionId;

    @Column(name="action_name")
    private String actionName;

    @ManyToOne
    private Task task;

    public TaskAction(String actionName, Task task) {
        this.actionName = actionName;
        this.task = task;
    }

    public int getTaskActionId() {
        return taskActionId;
    }
}
