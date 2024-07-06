package com.project.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class    NextTaskInput {
    private String firstTask;
    private int workflowId;
    private List<TaskActionInput> actionNextTask;
}
