package com.project.workflow.service;

import com.project.workflow.entity.*;
import com.project.workflow.repository.*;
import com.project.workflow.utility.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.project.workflow.utility.Status.COMPLETE;

@Service
public class TaskInstanceService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskInstanceRepository taskInstanceRepository;

    @Autowired
    private TaskUserInstanceService taskUserInstanceService;

    @Autowired
    TaskUserInstanceRepository taskUserInstanceRepository;

    @Autowired
    TaskActionRepository taskActionRepository;

    @Autowired
    NextTaskRepository nextTaskRepository;

    @Autowired
    RoleService roleService;

    public void createFirstTask(WorkflowInstance workflowInstance, User instantiator){
        Workflow workflow = workflowInstance.getWorkflow();
        Task task = taskRepository.findByWorkflowAndIsFirstTask(workflow, true).get(0);

        TaskInstance taskInstance = new TaskInstance(
                task, workflowInstance, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), true
        );

        taskInstanceRepository.save(taskInstance);
        taskUserInstanceService.createFistTaskUserInstance(taskInstance, instantiator);
    }

    public List<TaskInstance> readByWorkflowInstance(WorkflowInstance workflowInstance){
        return taskInstanceRepository.findByWorkflowInstance(workflowInstance);
    }
}

