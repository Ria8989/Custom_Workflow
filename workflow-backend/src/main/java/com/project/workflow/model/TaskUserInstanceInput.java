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
public class TaskUserInstanceInput {
    private Integer taskUserInstanceId;
    private String actionStatus;
    private Status taskUserStatus;
    private String uploadFilePath = null;
    private String comment = null;
}
