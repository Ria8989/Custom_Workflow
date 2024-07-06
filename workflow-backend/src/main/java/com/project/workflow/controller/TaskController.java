package com.project.workflow.controller;

import com.project.workflow.entity.Task;
import com.project.workflow.model.NextTaskInput;
import com.project.workflow.model.TaskActionInput;
import com.project.workflow.model.TaskActionOutput;
import com.project.workflow.model.TaskInput;
import com.project.workflow.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping()
    public ResponseEntity<Task> saveTask(@RequestBody TaskInput taskInput){
        try {
            return ResponseEntity.of(Optional.of(taskService.createTask(taskInput)));
        }catch (Exception e){
            System.out.println("Error saving task : "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping()
    public ResponseEntity<Task> getTaskById(@RequestParam Integer taskId){
        try {
            return ResponseEntity.of(Optional.of(taskService.readTaskById(taskId)));
        }catch (Exception e){
            System.out.println("Error getting workflow : "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/first_task")
    public ResponseEntity<List<Task>> getFirstTask(@RequestParam Integer workflowId){
        try {
            return ResponseEntity.of(Optional.of(taskService.readFirstTask(workflowId)));
        }catch (Exception e){
            System.out.println("Error getting all workflow : "+e);
            return ResponseEntity.status(500).build();
        }

    }

    @GetMapping("/all_task")
    public ResponseEntity<List<Task>> getAllTask(@RequestParam Integer workflowId){
        try {
            return ResponseEntity.of(Optional.of(taskService.readAllTask(workflowId)));
        }catch (Exception e){
            System.out.println("Error getting all workflow : "+e);
            return ResponseEntity.status(500).build();
        }

    }
    @GetMapping("/link_task")
    public ResponseEntity<List<TaskActionOutput>> linkTask(@RequestParam Integer workflowId){
        try {
            return ResponseEntity.of(Optional.of(taskService.link(workflowId)));
        }catch (Exception e){
            System.out.println("Error getting task for linking: "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/link")
    public String linkTask(@RequestBody NextTaskInput nextTaskInput){
        try {
            for(TaskActionInput taskActionInput: nextTaskInput.getActionNextTask()){
                System.out.println(taskActionInput.getAction()+" *** "+ taskActionInput.getNextTaskId().get(0));
            }
            taskService.linkAllTask(nextTaskInput);
            return "Successfully linked task";
        }catch (Exception e){
            System.out.println("Error linking task : "+e);
            return "Error linking task";
        }
    }
}

