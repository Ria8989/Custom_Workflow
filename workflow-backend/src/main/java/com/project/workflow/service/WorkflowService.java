package com.project.workflow.service;

import com.project.workflow.entity.*;
import com.project.workflow.model.WorkflowInput;
import com.project.workflow.repository.RoleRepository;
import com.project.workflow.repository.UserRepository;
import com.project.workflow.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WorkflowService {

    @Autowired
    WorkflowRepository workflowRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TaskService taskService;

    public Workflow createWorkflow(WorkflowInput workflow){
        User user = userRepository.findByUserEmail(workflow.getUserEmail());
        Workflow newWorkflow;
        if(workflow.getIsAllRole()){
            newWorkflow = new Workflow(
                    workflow.getWorkflowName(),
                    workflow.getWorkflowDescription(),
                    user,
                    true);
        }else {
            newWorkflow = new Workflow(
                    workflow.getWorkflowName(),
                    workflow.getWorkflowDescription(),
                    user);
            for (String roleName:
                    workflow.getWorkflowRole()) {
                Role role = roleRepository.findByRoleName(roleName);
                System.out.println("found role with "+roleName);
                newWorkflow.addRole(role);
                System.out.println("we have updated the workflow with "+role.getRoleName());
            }
        }
        newWorkflow = workflowRepository.save(newWorkflow);
        taskService.createEndTask(newWorkflow);
        return newWorkflow;
    }

    public Optional<Workflow> readWorkflowById(Integer id){
        return workflowRepository.findById(id);
    }

    public List<Workflow> readAllWorkflow(){
        return workflowRepository.findAll();
    }

    public Set<Workflow> readWorkflowByUser(String email){
        User user = userRepository.findByUserEmail(email);
        Set<Role> roles = user.getRoles();
        Set<Workflow> userWorkflow = new HashSet<>();

        List<Workflow> allUserWorkflow = workflowRepository.findByIsAllRole(true);
        for (Workflow workflow: allUserWorkflow) {
            userWorkflow.add(workflow);
        }

        for (Role role: roles) {
            List<Workflow> allUserRoleWorkflow = workflowRepository.findByWorkflowroles(role);
            for (Workflow workflow: allUserRoleWorkflow) {
                userWorkflow.add(workflow);
            }
        }


        return userWorkflow;
    }

    public List<Workflow> readWorkflowByCreator(String email){
        User user = userRepository.findByUserEmail(email);
        List<Workflow> userWorkflow = workflowRepository.findByCreator(user);
        return userWorkflow;
    }
}
