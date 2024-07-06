package com.project.workflow.controller;

import com.project.workflow.entity.Workflow;
import com.project.workflow.model.WorkflowInput;
import com.project.workflow.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/workflow")
public class WorkflowController {

    @Autowired
    WorkflowService workflowService;

    @PostMapping()
    public ResponseEntity<Workflow> saveWorkflow(@RequestBody WorkflowInput workflow){
        try {
            return ResponseEntity.of(Optional.of(workflowService.createWorkflow(workflow)));
        }catch (Exception e){
            System.out.println("Error saving workflow : "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping()
    public ResponseEntity<Optional<Workflow>> getWorkflowById(@RequestParam Integer workflowId){
        try {
            return ResponseEntity.of(Optional.of(workflowService.readWorkflowById(workflowId)));
        }catch (Exception e){
            System.out.println("Error getting workflow : "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Workflow>> getAllWorkflow(){
        try {
            return ResponseEntity.of(Optional.of(workflowService.readAllWorkflow()));
        }catch (Exception e){
            System.out.println("Error getting all workflow : "+e);
            return ResponseEntity.status(500).build();
        }

    }

    @GetMapping("/user")
    public ResponseEntity<Set<Workflow>> getWorkflowByUser(@RequestParam String email){
        try {
            return ResponseEntity.of(Optional.of(workflowService.readWorkflowByUser(email)));
        }catch (Exception e){
            System.out.println("Error getting workflow : "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/creator")
    public ResponseEntity<List<Workflow>> getWorkflowByCreator(@RequestParam String email){
        try {
            return ResponseEntity.of(Optional.of(workflowService.readWorkflowByCreator(email)));
        }catch (Exception e){
            System.out.println("Error getting workflow by creator: "+e);
            return ResponseEntity.status(500).build();
        }
    }
}

