package com.project.workflow.controller;

import com.project.workflow.entity.TaskInstance;
import com.project.workflow.entity.TaskUserInstance;
import com.project.workflow.entity.WorkflowInstance;
import com.project.workflow.model.WorkflowInstanceInput;
import com.project.workflow.model.WorkflowInstanceStatusInput;
import com.project.workflow.service.WorkflowInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/workflow_instance")
public class WorkflowInstanceController {

    @Autowired
    WorkflowInstanceService workflowInstanceService;

    @PostMapping()
    public ResponseEntity<WorkflowInstance> saveWorkflowInstance(@RequestBody WorkflowInstanceInput workflowInstanceInput){
        try {
            return ResponseEntity.of(Optional.of(workflowInstanceService.createWorkflowInstance(workflowInstanceInput)));
        }catch (Exception e){
            System.out.println("Error saving workflow instance : "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<WorkflowInstance>> getWorkflowInstanceByWorkflow(@RequestParam Integer workflowId){
        try {
            return ResponseEntity.of(Optional.of(workflowInstanceService.readWorkflowInstanceByWorkflow(workflowId)));
        }catch (Exception e){
            System.out.println("Error getting workflow instances: "+e);
            return ResponseEntity.status(500).build();
        }
    }

    /*
    @GetMapping()
    public ResponseEntity<WorkflowInstance> getWorkflowInstanceById(@RequestParam Integer workflowTaskInstanceId){
        try {
            return ResponseEntity.of(Optional.of(workflowInstanceService.readWorkflowInstanceById(workflowTaskInstanceId)));
        }catch (Exception e){
            System.out.println("Error getting workflow : "+e);
            return ResponseEntity.status(500).build();
        }
    }
     */

    @GetMapping("/all")
    public ResponseEntity<List<WorkflowInstance>> getAllWorkflowInstance(){
        try {
            return ResponseEntity.of(Optional.of(workflowInstanceService.readAllWorkflowInstance()));
        }catch (Exception e){
            System.out.println("Error getting all workflow instance: "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/status")
    public ResponseEntity<WorkflowInstance> updateWorkflowInstanceStatus(@RequestBody WorkflowInstanceStatusInput workflowInstanceStatusInput){
        try {
            return ResponseEntity.of(Optional.of(workflowInstanceService.updateWorkflowInstanceStatus(workflowInstanceStatusInput)));
        }catch (Exception e){
            System.out.println("Error updating workflow instance : "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<WorkflowInstance>> getWorkflowInstanceByWorkflow(@RequestParam String email){
        try {
            return ResponseEntity.of(Optional.of(workflowInstanceService.readWorkflowInstanceByUser(email)));
        }catch (Exception e){
            System.out.println("Error getting workflow instances: "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/task_instance")
    public ResponseEntity<List<TaskInstance>> getTaskInstanceByWorkflow(@RequestParam Integer workflowInstanceId){
        try {
            return ResponseEntity.of(Optional.of(workflowInstanceService.readTaskInstanceByWorkflowInstance(workflowInstanceId)));
        }catch (Exception e){
            System.out.println("Error getting task instances: "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/task_user_instance")
    public ResponseEntity<Set<TaskUserInstance>> getTaskUserInstanceByWorkflow(@RequestParam Integer workflowInstanceId){
        try {
            return ResponseEntity.of(Optional.of(workflowInstanceService.readTaskUserInstanceByWorkflowInstance(workflowInstanceId)));
        }catch (Exception e){
            System.out.println("Error getting task user instances: "+e);
            return ResponseEntity.status(500).build();
        }
    }
}

