package com.project.workflow.controller;

import com.project.workflow.entity.TaskUserInstance;
import com.project.workflow.model.TaskUserInstanceInput;
import com.project.workflow.service.TaskUserInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/task_user_instance")
public class TaskUserInstanceController {

    @Autowired
    TaskUserInstanceService taskUserInstanceService;

    @GetMapping()
    public ResponseEntity<List<TaskUserInstance>> getByUser(@RequestParam String email){
        try {
            return ResponseEntity.of(Optional.of(taskUserInstanceService.getTaskUserInstanceByUser(email)));
        }catch (Exception e){
            System.out.println("Error getting workflow instances: "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping()
    public ResponseEntity<TaskUserInstance> finishTask(@RequestBody TaskUserInstanceInput taskUserInstanceInput){
        try {
            return ResponseEntity.of(Optional.of(taskUserInstanceService.updateTaskUserInstance(taskUserInstanceInput)));

        }catch (Exception e){
            System.out.println("Error getting workflow instances: "+e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/previous_task_user_instance")
    public ResponseEntity<List<TaskUserInstance>> getByPresentTaskUserInstance(@RequestParam Integer taskUserInstanceId){
        try {
            return ResponseEntity.of(Optional.of(taskUserInstanceService.previousTaskUserInstance(taskUserInstanceId)));
        }catch (Exception e){
            System.out.println("Error getting workflow instances: "+e);
            return ResponseEntity.status(500).build();
        }
    }

}
