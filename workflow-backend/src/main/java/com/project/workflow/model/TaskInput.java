package com.project.workflow.model;

import com.project.workflow.utility.TaskUserType;
import com.project.workflow.utility.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskInput {
    private String taskName;
    private String taskDescription;
    private Type taskType=Type.MANDATORY;
    private Integer workflowId;
    private TaskUserType taskUserType;
    private List<String> taskUsers = new ArrayList<>();
    private List<String> taskRoles = new ArrayList<>();
    private List<String> taskActions = new ArrayList<>();
    private Boolean enableUpload = false;
    private Type uploadType;
    private Boolean allUser = true;
}
