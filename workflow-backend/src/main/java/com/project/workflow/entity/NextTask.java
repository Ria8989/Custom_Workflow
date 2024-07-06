package com.project.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="next_task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NextTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="task_action_id")
    private int taskActionId;

    @ManyToOne
    @JoinColumn(name = "next_task_id", referencedColumnName = "task_id" )
    private Task nextTask;

    public NextTask(int taskActionId, Task task) {
        this.taskActionId = taskActionId;
        this.nextTask = task;
    }
}
