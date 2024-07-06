package com.project.workflow.model;

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
public class WorkflowInput {
    private String workflowName;
    private String workflowDescription;
    private String userEmail;
    private List<String> workflowRole = new ArrayList<>();
    private Boolean isAllRole = false;
}
