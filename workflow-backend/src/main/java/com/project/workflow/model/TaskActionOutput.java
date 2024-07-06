package com.project.workflow.model;

import com.project.workflow.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskActionOutput {
    private String actionName;
    private Task task;
}
