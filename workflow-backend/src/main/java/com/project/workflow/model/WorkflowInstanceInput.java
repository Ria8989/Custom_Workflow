package com.project.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class WorkflowInstanceInput {

    private Integer workflowId;
    private String workflowInstantiatorEmail;

}
