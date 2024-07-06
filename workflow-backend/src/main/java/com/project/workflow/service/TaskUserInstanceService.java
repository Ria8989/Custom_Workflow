package com.project.workflow.service;

import com.project.workflow.entity.*;
import com.project.workflow.model.TaskUserInstanceInput;
import com.project.workflow.repository.*;
import com.project.workflow.utility.TaskUserType;
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
public class TaskUserInstanceService {

    @Autowired
    private TaskInstanceRepository taskInstanceRepository;

    @Autowired
    NextTaskRepository nextTaskRepository;

    @Autowired
    TaskUserInstanceRepository taskUserInstanceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskActionRepository taskActionRepository;

    @Autowired
    WorkflowInstanceRepository workflowInstanceRepository;

    @Autowired
    private EmailSenderService emailSenderService;


    public void createFistTaskUserInstance(TaskInstance taskInstance, User instantiator){

        Task task = taskInstance.getTask();
        /*
        List<TaskAction> taskActions = taskActionRepository.findByTask(task);
        Set<String> eligibleActionSet= new HashSet<>();

        for (TaskAction taskAction:
                taskActions) {
            eligibleActionSet.add(taskAction.getActionName());
        }

         */

        String eligibleAction = task.getEligibleAction();


        if(task.getTaskUserType().name().compareTo(TaskUserType.INITIATOR.name())==0){
            TaskUserInstance taskUserInstance = taskUserInstanceRepository.save(new TaskUserInstance(
                    taskInstance,
                    instantiator,
                    eligibleAction,
                    Timestamp.from(Instant.now()),
                    Timestamp.from(Instant.now())

            ));

            emailSenderService.sendSimpleEmail("riyapatidar29@gmail.com",
                    "First Task Instance Created with Task"+ taskUserInstance.getTaskInstance().getTask().getTaskName() ,
                    "Task Instance");



            /*
            emailSenderService.sendSimpleEmail("riyapatidar29@gmail.com",
                    "First Task Instance Created with Task"+ taskUserInstance.getTaskInstance().getTask().getTaskName() ,
                    "Task Instance");

             */

        }else if(task.getTaskUserType().name().compareTo(TaskUserType.ROLE.name())==0){
            Set<User> users = new HashSet<>();
            Set<Role> roles = task.getTaskroles();
            for (Role role:
                    roles) {
                List<User> roleUsers = userRepository.findByRoles(role);
                for (User roleUser:
                        roleUsers) {
                    users.add(roleUser);
                }
            }

            for (User user:
                    users) {
                taskUserInstanceRepository.save(new TaskUserInstance(
                        taskInstance,
                        user,
                        eligibleAction,
                        Timestamp.from(Instant.now()),
                        Timestamp.from(Instant.now())
                ));
            }

        }else if(task.getTaskUserType().name().compareTo(TaskUserType.USER.name())==0){
            Set<User> users = task.getTaskusers();
            for (User user:
                    users) {
                taskUserInstanceRepository.save(new TaskUserInstance(
                        taskInstance,
                        user,
                        eligibleAction,
                        Timestamp.from(Instant.now()),
                        Timestamp.from(Instant.now())
                ));
            }
        }
    }

    public void createTaskUserInstance(TaskInstance taskInstance){

        Task task = taskInstance.getTask();

        User instantiator= taskInstance.getWorkflowInstance().getWorkflowInstantiator();
        /*
        List<TaskAction> taskActions = taskActionRepository.findByTask(task);

        Set<String> eligibleActionSet= new HashSet<>();

        for (TaskAction taskAction:
                taskActions) {
            eligibleActionSet.add(taskAction.getActionName());
        }

        String eligibleAction = String.join(",", eligibleActionSet);
         */

        String eligibleAction = task.getEligibleAction();

        if(task.getTaskUserType().name().compareTo(TaskUserType.INITIATOR.name())==0){
            taskUserInstanceRepository.save(new TaskUserInstance(
                    taskInstance,
                    instantiator,
                    eligibleAction,
                    Timestamp.from(Instant.now()),
                    Timestamp.from(Instant.now())

            ));
            emailSenderService.sendSimpleEmail("riyapatidar29@gmail.com",
                    "New Task inbox: "+instantiator+" needs to perform "+ taskInstance.getTask().getTaskName(), "New Task");

        }else if(task.getTaskUserType().name().compareTo(TaskUserType.ROLE.name())==0){
            Set<User> users = new HashSet<>();
            Set<Role> roles = task.getTaskroles();
            for (Role role:
                    roles) {
                List<User> roleUsers = userRepository.findByRoles(role);
                for (User roleUser:
                        roleUsers) {
                    users.add(roleUser);
                }
            }

            for (User user:
                    users) {
                taskUserInstanceRepository.save(new TaskUserInstance(
                        taskInstance,
                        user,
                        eligibleAction,
                        Timestamp.from(Instant.now()),
                        Timestamp.from(Instant.now())
                ));

                emailSenderService.sendSimpleEmail("riyapatidar29@gmail.com",
                        "New Task inbox: "+user.getUserName()+" needs to perform "+ taskInstance.getTask().getTaskName(), "New Task");
            }

        }else if(task.getTaskUserType().name().compareTo(TaskUserType.USER.name())==0){
            Set<User> users = task.getTaskusers();
            for (User user:
                    users) {
                taskUserInstanceRepository.save(new TaskUserInstance(
                        taskInstance,
                        user,
                        eligibleAction,
                        Timestamp.from(Instant.now()),
                        Timestamp.from(Instant.now())
                ));

                emailSenderService.sendSimpleEmail("riyapatidar29@gmail.com",
                        "New Task inbox: "+user.getUserName()+" needs to perform "+ taskInstance.getTask().getTaskName(), "New Task");
            }
        }
    }

    //update call from frontend to here direction
    public TaskUserInstance updateTaskUserInstance(TaskUserInstanceInput taskUserInstanceInput){
        TaskUserInstance taskUserInstance = taskUserInstanceRepository
                .findById(taskUserInstanceInput.getTaskUserInstanceId()).get();
        taskUserInstance.setTaskUserInstanceLastUpdate(Timestamp.from(Instant.now()));
        taskUserInstance.setActionStatus(taskUserInstanceInput.getActionStatus());
        taskUserInstance.setTaskUserStatus(taskUserInstanceInput.getTaskUserStatus());
        taskUserInstance.setUploadFilePath(taskUserInstanceInput.getUploadFilePath());
        taskUserInstance.setComment(taskUserInstanceInput.getComment());

        taskUserInstance=taskUserInstanceRepository.save(taskUserInstance);
        updateTaskInstanceAndCreateNextTask(taskUserInstance);
        //updateTaskInstanceAndCreateNextTaskCustom(taskUserInstance);
        return taskUserInstance;
    }

    public void updateTaskInstanceAndCreateNextTask(TaskUserInstance presentTaskUserInstance){
        //check all user instances of present task is complete
        //check all user next instances of previous task is complete
        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
        if(taskInstance.getTaskStatus()!= COMPLETE){
            Boolean allUser = taskInstance.getTask().getAllUser();
            Boolean completed;
            if(allUser){
                completed = false;
                List<TaskUserInstance> taskUserInstances= taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance: taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) == 0) {
                        completed = true;

                    }else{
                        completed = false;
                        break;
                    }
                }
            } else {
                completed = false;
                List<TaskUserInstance> taskUserInstances= taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance: taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) == 0) {
                        completed = true;
                        break;
                    }
                }
            }

            if(completed==true){
                taskInstance.setTaskStatus(COMPLETE);
                taskInstance.setTaskInstanceLastUpdate(Timestamp.from(Instant.now()));
                taskInstanceRepository.save(taskInstance);

                if(taskInstance.getIsFirstTaskInstance()){
                    createNextTaskInstance(presentTaskUserInstance);
                } else {
                    Task task = taskInstance.getTask();
                    List<NextTask> nextTasks = nextTaskRepository.findByNextTask(task);
                    Set<Task> prevTasks = new HashSet<>();
                    WorkflowInstance workflowInstance = presentTaskUserInstance.getTaskInstance().getWorkflowInstance();

                    for(NextTask nextTask: nextTasks){
                        prevTasks.add(
                                taskActionRepository.findById(nextTask.getTaskActionId()).get().getTask()
                        );
                    }

                    // previous task status
                    Boolean createNewTask = false;

                    for(Task prevTask: prevTasks){
                        List<TaskInstance> taskInstanceTemps = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(prevTask, workflowInstance);

                        for(TaskInstance taskInstanceTemp: taskInstanceTemps) {
                            System.out.println("Previous Task found is " + prevTask.getTaskName());
                            if (taskInstanceTemp != null && taskInstanceTemp.getTask().getTaskName().compareTo("END") != 0) {
                                if (taskInstanceTemp.getTaskStatus() == COMPLETE) {
                                    createNewTask = true;
                                } else {
                                    createNewTask = false;
                                    break;
                                }
                            }
                        }
                    }

                    Boolean nextTaskStatus = false;
                    // previous tasks next task status

                    Set<Task> nextTasks1 = new HashSet<>();
                    for(Task prevTask: prevTasks) {
                        List<TaskAction> taskActions = taskActionRepository.findByTask(prevTask);
                        for (TaskAction taskAction : taskActions) {
                            List<NextTask> tempsNextTask = nextTaskRepository.findByTaskActionId(taskAction.getTaskActionId());
                            for (NextTask tempNextTask : tempsNextTask) {
                                nextTasks1.add(tempNextTask.getNextTask());
                            }
                        }
                    }
                    Boolean breakAgain = false;

                    for(Task nextTask: nextTasks1){
                        List<TaskInstance> taskInstanceTemps = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(nextTask, workflowInstance);
                        for(TaskInstance taskInstanceTemp: taskInstanceTemps) {
                            System.out.println("Previous' Next Task found is " + nextTask.getTaskName());
                            if (taskInstanceTemp != null && taskInstanceTemp.getTask().getTaskName().compareTo("END") != 0) {
                                if (taskInstanceTemp.getTaskStatus() == COMPLETE) {
                                    nextTaskStatus = true;
                                } else {
                                    nextTaskStatus = false;
                                    breakAgain =true;
                                    break;
                                }
                            }
                        }
                        if(breakAgain){
                            break;
                        }
                    }

                    if(createNewTask && nextTaskStatus){
                        createNextTaskInstance(presentTaskUserInstance);
                    }
                }
            }

        }

    }


    public void createNextTaskInstance(TaskUserInstance presentTaskUserInstance){
        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
        Task task = taskInstance.getTask();
        String actionStatus = presentTaskUserInstance.getActionStatus();
        TaskAction taskAction = taskActionRepository.findByActionNameAndTask(actionStatus, task);
        List<NextTask> nextTasks = nextTaskRepository.findByTaskActionId(taskAction.getTaskActionId());
        for(NextTask nextTask:  nextTasks){
            if (nextTask.getNextTask().getTaskName().compareTo("END")==0) {
                System.out.println("We reached end of the workflow :)");


                taskInstance.getWorkflowInstance().setWorkflowStatus(COMPLETE);
                taskInstance.setTaskInstanceLastUpdate(Timestamp.from(Instant.now()));
                taskInstanceRepository.save(taskInstance);
                WorkflowInstance workflowInstance = taskInstance.getWorkflowInstance();
                workflowInstance.setWorkflowInstanceLastUpdate(Timestamp.from(Instant.now()));
                workflowInstance.setWorkflowStatus(COMPLETE);
                emailSenderService.sendSimpleEmail("riyapatidar29@gmail.com",
                        "Workflow has ended "+workflowInstance.getWorkflow().getWorkflowName(), "Workflow completed");
                workflowInstanceRepository.save(workflowInstance);
                break;
            }
            TaskInstance nextTaskInstance = new TaskInstance(
                    nextTask.getNextTask(), taskInstance.getWorkflowInstance(), Timestamp.from(Instant.now())
            );
            taskInstanceRepository.save(nextTaskInstance);
            createTaskUserInstance(nextTaskInstance);
            /*
            TaskInstance temp = taskInstanceRepository.findByTaskAndWorkflowInstance(nextTask.getNextTask(), taskInstance.getWorkflowInstance());
            if(temp==null){
                TaskInstance nextTaskInstance = new TaskInstance(
                        nextTask.getNextTask(), taskInstance.getWorkflowInstance(), Timestamp.from(Instant.now())
                );
                taskInstanceRepository.save(nextTaskInstance);
                createTaskUserInstance(nextTaskInstance);
            }

             */


        }


    }

    public void updateTaskInstanceAndCreateNextTaskCustom(TaskUserInstance presentTaskUserInstance){
        //check all user instances of present task is complete
        //check all user next instances of previous task is complete
        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
        if(taskInstance.getTaskStatus()!= COMPLETE){
            Boolean allUser = taskInstance.getTask().getAllUser();
            Boolean completed;
            Set<Task> nestTasks = new HashSet<Task>();
            if(allUser){
                completed = false;
                List<TaskUserInstance> taskUserInstances= taskUserInstanceRepository.findByTaskInstance(taskInstance);


                for (TaskUserInstance taskUserInstance: taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) == 0) {

                        int taskActionId = taskActionRepository.findByActionNameAndTask(taskUserInstance.getActionStatus(), taskUserInstance.getTaskInstance().getTask()).getTaskActionId();
                        List<NextTask> nextTasksInstances = nextTaskRepository.findByTaskActionId(taskActionId);
                        for(NextTask nextTasksInstance: nextTasksInstances) {
                            nestTasks.add(nextTasksInstance.getNextTask());
                        }
                        completed = true;

                    }else{
                        completed = false;
                        break;
                    }
                }
            } else {
                completed = false;
                List<TaskUserInstance> taskUserInstances= taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance: taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) == 0) {
                        int taskActionId = taskActionRepository.findByActionNameAndTask(taskUserInstance.getActionStatus(), taskUserInstance.getTaskInstance().getTask()).getTaskActionId();
                        List<NextTask> nextTasksInstances = nextTaskRepository.findByTaskActionId(taskActionId);
                        for(NextTask nextTasksInstance: nextTasksInstances) {
                            nestTasks.add(nextTasksInstance.getNextTask());
                        }
                        completed = true;
                        break;
                    }
                }
            }

            if(completed==true){
                taskInstance.setTaskStatus(COMPLETE);
                taskInstance.setTaskInstanceLastUpdate(Timestamp.from(Instant.now()));
                taskInstanceRepository.save(taskInstance);

                if(taskInstance.getIsFirstTaskInstance()){
                    createNextTaskInstance(presentTaskUserInstance);
                } else {
                    Task task = taskInstance.getTask();
                    List<NextTask> nextTasks = nextTaskRepository.findByNextTask(task);
                    Set<Task> prevTasks = new HashSet<>();
                    WorkflowInstance workflowInstance = presentTaskUserInstance.getTaskInstance().getWorkflowInstance();

                    for(NextTask nextTask: nextTasks){
                        prevTasks.add(
                                taskActionRepository.findById(nextTask.getTaskActionId()).get().getTask()
                        );
                    }

                    // previous task status
                    Boolean createNewTask = false;
                    Boolean breakAgain=false;

                    for(Task prevTask: prevTasks){
                        List<TaskInstance> taskInstanceTemps = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(prevTask, workflowInstance);
                        for(TaskInstance taskInstanceTemp: taskInstanceTemps){
                            System.out.println("Previous Task found is "+prevTask.getTaskName());
                            if(taskInstanceTemp!=null && taskInstanceTemp.getTask().getTaskName().compareTo("END")!=0) {
                                if (taskInstanceTemp.getTaskStatus() == COMPLETE) {
                                    createNewTask = true;
                                } else {
                                    createNewTask = false;
                                    breakAgain = true;
                                    break;
                                }
                            }
                        }
                        if(breakAgain){
                            break;
                        }

                    }

                    Boolean nextTaskStatus = false;
                    // previous tasks next task status

                    Set<Task> nextTasks1 = new HashSet<>();
                    for(Task prevTask: prevTasks) {
                        List<TaskAction> taskActions = taskActionRepository.findByTask(prevTask);
                        for (TaskAction taskAction : taskActions) {
                            List<NextTask> tempsNextTask = nextTaskRepository.findByTaskActionId(taskAction.getTaskActionId());
                            for (NextTask tempNextTask : tempsNextTask) {
                                nextTasks1.add(tempNextTask.getNextTask());
                            }
                        }
                    }
                    breakAgain = false;

                    for(Task nextTask: nextTasks1){
                        List<TaskInstance> taskInstanceTemps = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(nextTask, workflowInstance);
                        for(TaskInstance taskInstanceTemp: taskInstanceTemps){
                            System.out.println("Previous' Next Task found is "+nextTask.getTaskName());
                            if(taskInstanceTemp!=null && taskInstanceTemp.getTask().getTaskName().compareTo("END")!=0) {
                                if (taskInstanceTemp.getTaskStatus() == COMPLETE) {
                                    nextTaskStatus = true;
                                } else {
                                    nextTaskStatus = false;
                                    breakAgain = true;
                                    break;
                                }
                            }
                        }
                        if(breakAgain){
                            break;
                        }

                    }

                    if(createNewTask && nextTaskStatus){
                        createNextTaskInstanceCustom(presentTaskUserInstance, nestTasks);
                    }
                }
            }

        }

    }

    public void createNextTaskInstanceCustom(TaskUserInstance presentTaskUserInstance, Set<Task> nextTasks){
        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();

        Task repeatedTask = null;
        Timestamp repeatedTaskTimeStamp = Timestamp.from(Instant.now());
        Set<Task> forwardTasks = new HashSet<Task>();

        for( Task nextTask: nextTasks){
            List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance
                    (taskInstanceRepository.findByTaskAndWorkflowInstance(nextTask, taskInstance.getWorkflowInstance()).get(0));

            if (taskUserInstances.size() > 0){
                for(TaskUserInstance taskUserInstance: taskUserInstances){
                    if(taskUserInstance.getTaskUserInstanceCreated().before(repeatedTaskTimeStamp)) {

                        repeatedTask = taskUserInstance.getTaskInstance().getTask();
                        System.out.println("Assigning repeated task "+repeatedTask.getTaskName());
                        repeatedTaskTimeStamp = taskUserInstance.getTaskInstance().getTaskInstanceCreated();
                    }
                }
            }else {
                forwardTasks.add(nextTask);
            }


        }

        if(repeatedTask == null){
            for (Task forwardTask: forwardTasks){
                if (forwardTask.getTaskName().compareTo("END")==0) {
                    System.out.println("We reached end of the workflow !!!");
                    taskInstance.getWorkflowInstance().setWorkflowStatus(COMPLETE);
                    taskInstance.setTaskInstanceLastUpdate(Timestamp.from(Instant.now()));
                    taskInstanceRepository.save(taskInstance);
                    WorkflowInstance workflowInstance = taskInstance.getWorkflowInstance();
                    workflowInstance.setWorkflowInstanceLastUpdate(Timestamp.from(Instant.now()));
                    workflowInstance.setWorkflowStatus(COMPLETE);

                    WorkflowInstance workflowInstance1 = workflowInstanceRepository.save(workflowInstance);
                    System.out.println("WorkflowInstance Id "+workflowInstance1.getWorkflowInstanceId() + " Status "+workflowInstance1.getWorkflowStatus());

                    emailSenderService.sendSimpleEmail("riyapatidar29@gmail.com",
                            "Workflow has ended "+workflowInstance.getWorkflow().getWorkflowName(), "Workflow completed");

                    break;
                }
                TaskInstance nextTaskInstance = new TaskInstance(
                        forwardTask, taskInstance.getWorkflowInstance(), Timestamp.from(Instant.now())
                );
                taskInstanceRepository.save(nextTaskInstance);
                createTaskUserInstance(nextTaskInstance);
            }
        } else {
            TaskInstance nextTaskInstance = new TaskInstance(
                    repeatedTask, taskInstance.getWorkflowInstance(), Timestamp.from(Instant.now())
            );
            taskInstanceRepository.save(nextTaskInstance);
            createTaskUserInstance(nextTaskInstance);
        }

    }

    public List<TaskUserInstance> getTaskUserInstanceByUser(String email){
        return taskUserInstanceRepository.findByUser(userRepository.findByUserEmail(email));
    }

    public List<TaskUserInstance> previousTaskUserInstance(Integer presentTaskUserInstanceId){
            TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(presentTaskUserInstanceId).get();
                TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
                Task task = taskInstance.getTask();
                List<NextTask> nextTasks = nextTaskRepository.findByNextTask(task);
                Set<Task> prevTasks = new HashSet<>();
                WorkflowInstance workflowInstance = presentTaskUserInstance.getTaskInstance().getWorkflowInstance();

                List<TaskUserInstance> previousTaskUserInstances = new ArrayList<>();

                for(NextTask nextTask: nextTasks){
                    prevTasks.add(
                            taskActionRepository.findById(nextTask.getTaskActionId()).get().getTask()
                    );
                }

        for(Task prevTask: prevTasks){
            List<TaskInstance> prevTaskInstanceTemps = taskInstanceRepository
                    .findByTaskAndWorkflowInstance(prevTask, workflowInstance);
            if(prevTaskInstanceTemps.size()>0){
                TaskInstance prevTaskInstanceTemp = prevTaskInstanceTemps.get(0);
            System.out.println("Previous Task found is "+prevTask.getTaskName());
            List<TaskUserInstance> prevTaskUserInstanceTemp = taskUserInstanceRepository.findByTaskInstance(prevTaskInstanceTemp);

            for(TaskUserInstance prevTaskUserInstance: prevTaskUserInstanceTemp) {
                previousTaskUserInstances.add(prevTaskUserInstance);

            }
        }

            }

        return previousTaskUserInstances;


    }

}
