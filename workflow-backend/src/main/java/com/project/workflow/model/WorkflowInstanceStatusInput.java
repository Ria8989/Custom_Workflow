package com.project.workflow.model;

import com.project.workflow.utility.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class WorkflowInstanceStatusInput {
    private Integer workflowInstanceId;
    private Status workflowStatus;
}
