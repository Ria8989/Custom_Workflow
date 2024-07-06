package com.project.workflow.service;

import com.project.workflow.entity.NextTask;
import com.project.workflow.entity.TaskAction;
import com.project.workflow.entity.Task;
import com.project.workflow.entity.Workflow;
import com.project.workflow.model.NextTaskInput;
import com.project.workflow.model.TaskActionInput;
import com.project.workflow.model.TaskActionOutput;
import com.project.workflow.model.TaskInput;
import com.project.workflow.repository.*;
import com.project.workflow.utility.TaskUserType;
import com.project.workflow.utility.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.project.workflow.utility.TaskUserType.USER;
import static com.project.workflow.utility.Type.MANDATORY;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    WorkflowRepository workflowRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskActionRepository taskActionRepository;

    @Autowired
    NextTaskRepository nextTaskRepository;

    public Task createTask(TaskInput taskInput)  {

        Workflow workflow = workflowRepository.findById(taskInput.getWorkflowId()).get();

        Boolean enableUpload = taskInput.getEnableUpload();
        Type uploadType = null;
        if(enableUpload){
            uploadType = taskInput.getUploadType();
        }

        Task task = new Task(taskInput.getTaskName(),
                taskInput.getTaskDescription(),
                taskInput.getTaskType(), workflow,
                taskInput.getTaskUserType(), enableUpload, uploadType,
                taskInput.getAllUser());

        if(taskInput.getTaskUserType().name().compareTo(USER.name())==0){
            for (String taskUsers: taskInput.getTaskUsers()
            ) {
                task.addUsers(userRepository.findByUserEmail(taskUsers));
            }
        }

        if(taskInput.getTaskUserType().name().compareTo(TaskUserType.ROLE.name())==0){
            for (String taskRoles: taskInput.getTaskRoles()
            ) {
                task.addRoles(roleRepository.findByRoleName(taskRoles));
            }
        }
        String eligibleAction = String.join(",", taskInput.getTaskActions());
        task.setEligibleAction(eligibleAction);

        Task newTask =  taskRepository.save(task);

        for (String action: taskInput.getTaskActions()
        ) {
            taskActionRepository.save(new TaskAction(action, task));
        }

        return newTask;
    }

    public Task readTaskById(Integer id){
        return taskRepository.findById(id).get();
    }

    public List<Task> readFirstTask(Integer workflowId){
        Workflow workflow = workflowRepository.findById(workflowId).get();
        return taskRepository.findByWorkflowAndIsFirstTask(workflow, true);
    }

    public List<Task> readAllTask(Integer workflowId){
        Workflow workflow = workflowRepository.findById(workflowId).get();
        return taskRepository.findByWorkflow(workflow);
    }

    public void linkAllTask(NextTaskInput nextTaskInput){
        Task firstTask = taskRepository.findByTaskNameAndWorkflow(
                nextTaskInput.getFirstTask(),
                workflowRepository.findById(nextTaskInput.getWorkflowId()).get()
        );
        firstTask.setIsFirstTask(true);

        for (TaskActionInput taskActionInput: nextTaskInput.getActionNextTask()
        ) {
            Task task = taskRepository.findById(taskActionInput.getTaskId()).get();
            System.out.println("Task found "+task.getTaskName());
            System.out.println("Action found found "+taskActionInput.getAction());
            TaskAction taskAction = taskActionRepository.findByActionNameAndTask(taskActionInput.getAction(), task);
            System.out.println("Task Action found found "+taskAction);

            for (Integer nextTaskId: taskActionInput.getNextTaskId()
            ) {
                NextTask nextTask = new NextTask(taskAction.getTaskActionId(),
                        taskRepository.findById(nextTaskId).get());
                nextTaskRepository.save(nextTask);
            }

        }

    }

    public List<TaskActionOutput> link(Integer workflowId){
        Workflow workflow = workflowRepository.findById(workflowId).get();
        List<Task> tasks = taskRepository.findByWorkflow(workflow);
        Collections.sort(tasks);
        List<TaskActionOutput> taskActionOutputs = new ArrayList<>();

        for(Task task: tasks){
            System.out.println("task is "+task.getTaskId());
            List<TaskAction> taskActions = taskActionRepository.findByTask(task);
            for(TaskAction taskAction: taskActions){
                taskActionOutputs.add(new TaskActionOutput(taskAction.getActionName(), taskAction.getTask()));
            }
        }
        return taskActionOutputs;
    }

    public void createEndTask(Workflow workflow){
        Task task = new Task(
                "END",
                "END",
                MANDATORY,
                workflow,
                USER,
                false,
                null,
                true

        );
        taskRepository.save(task);
    }
}

