package com.project.workflow.service;

import com.project.workflow.entity.*;
import com.project.workflow.model.WorkflowInstanceInput;
import com.project.workflow.model.WorkflowInstanceStatusInput;
import com.project.workflow.repository.TaskUserInstanceRepository;
import com.project.workflow.repository.UserRepository;
import com.project.workflow.repository.WorkflowInstanceRepository;
import com.project.workflow.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WorkflowInstanceService {

    @Autowired
    WorkflowRepository workflowRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkflowInstanceRepository workflowInstanceRepository;

    @Autowired
    TaskInstanceService taskInstanceService;

    @Autowired
    TaskUserInstanceRepository taskUserInstanceRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public WorkflowInstance createWorkflowInstance(WorkflowInstanceInput workflowInstanceInput){

        User user = userRepository.findByUserEmail(workflowInstanceInput.getWorkflowInstantiatorEmail());
        Workflow workflow = workflowRepository.findById(workflowInstanceInput.getWorkflowId()).get();

        WorkflowInstance workflowInstance = new WorkflowInstance(
                workflow, user, Timestamp.from(Instant.now()), Timestamp.from(Instant.now())
        );

        workflowInstance=  workflowInstanceRepository.save(workflowInstance);

        /*
        emailSenderService.sendSimpleEmail("swadiworld@gmail.com",
                "Workflow Instance is  Initiated "+ workflowInstance.getWorkflow().getWorkflowName() +"by user "+user.getUserName(),
                "Workflow Instance Instance");

         */
        /*
        emailSenderService.sendSimpleEmail("riyapatidar29@gmail.com",
                "Workflow Instance is  Initiated "+ workflowInstance.getWorkflow().getWorkflowName() +"by user "+user.getUserName(),
                "Workflow Instance Instance");

         */

        taskInstanceService.createFirstTask(workflowInstance, user);

        return workflowInstance;
    }

    public List<WorkflowInstance> readWorkflowInstanceByWorkflow(Integer workflowId){
        Workflow workflow = workflowRepository.findById(workflowId).get();
        return workflowInstanceRepository.findByWorkflow(workflow);
    }

    public WorkflowInstance readWorkflowInstanceById(Integer workflowTaskInstanceId){
        return workflowInstanceRepository.findById(workflowTaskInstanceId).get();
    }

    public List<WorkflowInstance> readAllWorkflowInstance(){
        return workflowInstanceRepository.findAll();
    }

    public WorkflowInstance updateWorkflowInstanceStatus(WorkflowInstanceStatusInput workflowInstanceStatusInput){
        WorkflowInstance workflowInstance = workflowInstanceRepository.findById(workflowInstanceStatusInput.getWorkflowInstanceId()).get();
        workflowInstance.setWorkflowStatus(workflowInstanceStatusInput.getWorkflowStatus());
        workflowInstance.setWorkflowInstanceLastUpdate(Timestamp.from(Instant.now()));
        workflowInstance.setWorkflowInstanceLastUpdate(Timestamp.from(Instant.now()));
        return workflowInstanceRepository.save(workflowInstance);
    }

    public List<WorkflowInstance> readWorkflowInstanceByUser(String email){
        User user = userRepository.findByUserEmail(email);
        return workflowInstanceRepository.findByWorkflowInstantiator(user);
    }

    public List<TaskInstance> readTaskInstanceByWorkflowInstance(Integer workflowInstanceId){
        WorkflowInstance workflowInstance = workflowInstanceRepository.findById(workflowInstanceId).get();
        return  taskInstanceService.readByWorkflowInstance(workflowInstance);
    }

    public Set<TaskUserInstance> readTaskUserInstanceByWorkflowInstance(Integer workflowInstanceId){
        WorkflowInstance workflowInstance = workflowInstanceRepository.findById(workflowInstanceId).get();
        List<TaskInstance> taskInstances= taskInstanceService.readByWorkflowInstance(workflowInstance);
        Set<TaskUserInstance> taskUserInstances = new HashSet<>();

        for(TaskInstance taskInstance: taskInstances){
            List<TaskUserInstance> taskUserInstancesTemp = taskUserInstanceRepository.findByTaskInstance(taskInstance);
            for(TaskUserInstance taskUserInstance: taskUserInstancesTemp){
                taskUserInstances.add(taskUserInstance);
            }

        }
        return  taskUserInstances;
    }

}

