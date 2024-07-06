package com.project.workflow;

import com.project.workflow.entity.*;
import com.project.workflow.model.WorkflowInstanceInput;
import com.project.workflow.repository.*;
import com.project.workflow.service.RoleService;
import com.project.workflow.service.TaskInstanceService;
import com.project.workflow.service.WorkflowInstanceService;
import com.project.workflow.utility.Status;
import com.project.workflow.utility.TaskUserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.project.workflow.utility.Status.COMPLETE;
import static com.project.workflow.utility.Status.PENDING;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class WorkInstanceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkflowRepository workflowRepository;

    @Autowired
    WorkflowInstanceRepository workflowInstanceRepository;

    @Autowired
    TaskInstanceRepository taskInstanceRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskUserInstanceRepository taskUserInstanceRepository;

    @Autowired
    TaskActionRepository taskActionRepository;

    @Autowired
    NextTaskRepository nextTaskRepository;

    @Test
    public void testInstanceFlow(){

        // create workflow instance

        /*
        WorkflowInstanceInput workflowInstanceInput = new WorkflowInstanceInput(
                1, "ron@gmail.com"
        );
        User user = userRepository.findByUserEmail(workflowInstanceInput.getWorkflowInstantiatorEmail());
        Workflow workflow = workflowRepository.findById(workflowInstanceInput.getWorkflowId()).get();

        WorkflowInstance workflowInstance = new WorkflowInstance(
                workflow, user, Timestamp.from(Instant.now())
        );

        workflowInstance=  workflowInstanceRepository.save(workflowInstance);

        System.out.println("Workflow instance created is "+ workflowInstance);
        */


        //create first task instance
        /*
        WorkflowInstance workflowInstance=  workflowInstanceRepository.findById(2).get();

        Workflow workflow = workflowInstance.getWorkflow();
        Task task = taskRepository.findByWorkflowAndIsFirstTask(workflow, true).get(0);

        TaskInstance taskInstance = new TaskInstance(
                task, workflowInstance, Timestamp.from(Instant.now()), true
        );

        taskInstance=taskInstanceRepository.save(taskInstance);
        System.out.println("Task instance created is "+ taskInstance.getTaskInstanceId());
        */

        // create first tasks - task user instance
        /*
        TaskInstance taskInstance = taskInstanceRepository.findById(7).get();
        User instantiator = userRepository.findByUserEmail("ron@gmail.com");

        Task task = taskInstance.getTask();
        String eligibleAction = task.getEligibleAction();

        if(task.getTaskUserType().name().compareTo(TaskUserType.INITIATOR.name())==0){
            taskUserInstanceRepository.save(new TaskUserInstance(
                    taskInstance,
                    instantiator,
                    eligibleAction,
                    Timestamp.from(Instant.now())

            ));
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
                        Timestamp.from(Instant.now())
                ));
            }
        }
        */

        // update first task as completed

        /*
        TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(1).get();
        presentTaskUserInstance.setActionStatus("None");
        presentTaskUserInstance.setTaskUserStatus(COMPLETE);
        presentTaskUserInstance.setUploadFilePath("sample_file_upload");

        presentTaskUserInstance=taskUserInstanceRepository.save(presentTaskUserInstance);

       updateTaskInstanceAndCreateNextTask(presentTaskUserInstance);

        */

        // create new instance based on first task completion
        /*
        TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(1).get();
        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
        Task taskTemp = taskInstance.getTask();
        String actionStatus = presentTaskUserInstance.getActionStatus();
        TaskAction taskActionTemp = taskActionRepository.findByActionNameAndTask(actionStatus, taskTemp);
        List<NextTask> nextTasks = nextTaskRepository.findByTaskActionId(taskActionTemp.getTaskActionId());
        for(NextTask nextTask:  nextTasks){
            if(nextTask.getNextTask().getTaskName()=="END"){
                taskInstance.getWorkflowInstance().setWorkflowStatus(COMPLETE);
                break;
            }
            TaskInstance nextTaskInstance = new TaskInstance(
                    nextTask.getNextTask(), taskInstance.getWorkflowInstance(), Timestamp.from(Instant.now())
            );
            taskInstanceRepository.save(nextTaskInstance);
            System.out.println("Next Task Instance is "+nextTaskInstance.getTaskInstanceId());
            Task task = nextTaskInstance.getTask();
            User instantiator = nextTaskInstance.getWorkflowInstance().getWorkflowInstantiator();
            List<TaskAction> taskActions = taskActionRepository.findByTask(task);
            Set<String> eligibleActionSet= new HashSet<>();

            for (TaskAction taskAction:
                    taskActions) {
                eligibleActionSet.add(taskAction.getActionName());
            }

            String eligibleAction = String.join(",", eligibleActionSet);

            if(task.getTaskUserType().name().compareTo(TaskUserType.INITIATOR.name())==0){
                taskUserInstanceRepository.save(new TaskUserInstance(
                        nextTaskInstance,
                        instantiator,
                        eligibleAction,
                        Timestamp.from(Instant.now())

                ));
            }else if(task.getTaskUserType().name().compareTo(TaskUserType.ROLE.name())==0){
                Set<User> users = new HashSet<>();
                Set<Role> roles = task.getTaskroles();
                for (Role role:
                        roles) {
                    List<User> roleUsers = userRepository.findByRoles(role);
                    users.addAll(roleUsers);
                }

                for (User user:
                        users) {
                    taskUserInstanceRepository.save(new TaskUserInstance(
                            nextTaskInstance,
                            user,
                            eligibleAction,
                            Timestamp.from(Instant.now())
                    ));
                }

            }else if(task.getTaskUserType().name().compareTo(TaskUserType.USER.name())==0){
                Set<User> users = task.getTaskusers();
                for (User user:
                        users) {
                    taskUserInstanceRepository.save(new TaskUserInstance(
                            nextTaskInstance,
                            user,
                            eligibleAction,
                            Timestamp.from(Instant.now())
                    ));
                }
            }
        }
        */

        //update second task instance but not create next task instance
        /*
        TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(8).get();
        presentTaskUserInstance.setActionStatus("Skip");
        presentTaskUserInstance.setTaskUserStatus(COMPLETE);
        presentTaskUserInstance.setUploadFilePath("sample_file_upload_2");

        presentTaskUserInstance=taskUserInstanceRepository.save(presentTaskUserInstance);


        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
        if(taskInstance.getTaskStatus()!= COMPLETE) {
            Boolean allUser = taskInstance.getTask().getAllUser();
            Boolean completed;
            if (allUser) {
                completed = true;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = false;

                        break;
                    }
                }
            } else {
                completed = false;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = true;
                        break;
                    }
                }
            }

            if(completed==true){
                taskInstance.setTaskStatus(COMPLETE);
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
                        System.out.println("We added previous tasks "+taskActionRepository.findById(nextTask.getTaskActionId()).get().getTask().getTaskName());
                    }

                    List<TaskInstance> prevTaskInstances;
                    Boolean createNewTask = false;

                    for(Task prevTask: prevTasks){
                        TaskInstance taskInstanceTemp = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(prevTask, workflowInstance);
                        if(taskInstanceTemp.getTaskStatus() == COMPLETE){
                            createNewTask = true;
                        }else{
                            createNewTask = false;
                        }
                    }

                    if(createNewTask){
                        createNextTaskInstance(presentTaskUserInstance);
                    }


                }
            }

         */

        //update third task and also create new task
        /*
        TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(9).get();
        presentTaskUserInstance.setActionStatus("Skip");
        presentTaskUserInstance.setTaskUserStatus(COMPLETE);
        presentTaskUserInstance.setUploadFilePath("sample_file_upload_2");

        presentTaskUserInstance=taskUserInstanceRepository.save(presentTaskUserInstance);


        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
        if(taskInstance.getTaskStatus()!= COMPLETE) {
            Boolean allUser = taskInstance.getTask().getAllUser();
            Boolean completed;
            if (allUser) {
                completed = true;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = false;

                        break;
                    }
                }
            } else {
                completed = false;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = true;
                        break;
                    }
                }
            }

            if(completed==true){
                taskInstance.setTaskStatus(COMPLETE);
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
                        System.out.println("We added previous tasks "+taskActionRepository.findById(nextTask.getTaskActionId()).get().getTask().getTaskName());
                    }

                    List<TaskInstance> prevTaskInstances;
                    Boolean createNewTask = false;

                    for(Task prevTask: prevTasks){
                        TaskInstance taskInstanceTemp = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(prevTask, workflowInstance);
                        if(taskInstanceTemp.getTaskStatus() == COMPLETE){
                            createNewTask = true;
                        }else{
                            createNewTask = false;
                        }
                    }

                    if(createNewTask){
                        createNextTaskInstance(presentTaskUserInstance);
                    }


                }
            }

        }
        */

        // update 4th task and create 5th task
        /*
        TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(11).get();
        presentTaskUserInstance.setActionStatus("Approve");
        presentTaskUserInstance.setTaskUserStatus(COMPLETE);

        presentTaskUserInstance=taskUserInstanceRepository.save(presentTaskUserInstance);


        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
        if(taskInstance.getTaskStatus()!= COMPLETE) {
            Boolean allUser = taskInstance.getTask().getAllUser();
            Boolean completed;
            if (allUser) {
                completed = true;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = false;

                        break;
                    }
                }
            } else {
                completed = false;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = true;
                        break;
                    }
                }
            }

            if(completed==true){
                taskInstance.setTaskStatus(COMPLETE);
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
                        System.out.println("We added previous tasks "+taskActionRepository.findById(nextTask.getTaskActionId()).get().getTask().getTaskName());
                    }

                    List<TaskInstance> prevTaskInstances;
                    Boolean createNewTask = false;

                    for(Task prevTask: prevTasks){
                        TaskInstance taskInstanceTemp = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(prevTask, workflowInstance);
                        if(taskInstanceTemp.getTaskStatus() == COMPLETE){
                            createNewTask = true;
                        }else{
                            createNewTask = false;
                        }
                    }

                    if(createNewTask){
                        createNextTaskInstance(presentTaskUserInstance);
                    }


                }
            }

         */

        //create 5th task and complete workflow
        /*
        TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(12).get();
        presentTaskUserInstance.setActionStatus("Approve");
        presentTaskUserInstance.setTaskUserStatus(COMPLETE);

        presentTaskUserInstance=taskUserInstanceRepository.save(presentTaskUserInstance);


        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
        if(taskInstance.getTaskStatus()!= COMPLETE) {
            Boolean allUser = taskInstance.getTask().getAllUser();
            Boolean completed;
            if (allUser) {
                completed = true;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = false;

                        break;
                    }
                }
            } else {
                completed = false;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = true;
                        break;
                    }
                }
            }

            if(completed==true){
                taskInstance.setTaskStatus(COMPLETE);
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
                        System.out.println("We added previous tasks "+taskActionRepository.findById(nextTask.getTaskActionId()).get().getTask().getTaskName());
                    }

                    List<TaskInstance> prevTaskInstances;
                    Boolean createNewTask = false;

                    for(Task prevTask: prevTasks){
                        TaskInstance taskInstanceTemp = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(prevTask, workflowInstance);
                        if(taskInstanceTemp.getTaskStatus() == COMPLETE){
                            createNewTask = true;
                        }else{
                            createNewTask = false;
                        }
                    }

                    if(createNewTask){
                        createNextTaskInstance(presentTaskUserInstance);
                    }


                }
            }

        // just lots of checks back and fourth

         */
        /*
        TaskInstance tempTask = taskInstanceRepository.findById(14).get();
        tempTask.setTaskStatus(PENDING);
        taskInstanceRepository.save(tempTask);
        TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(13).get();
        presentTaskUserInstance.setActionStatus("Approve");
        presentTaskUserInstance.setTaskUserStatus(COMPLETE);

        presentTaskUserInstance=taskUserInstanceRepository.save(presentTaskUserInstance);
        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
        if(taskInstance.getTaskStatus()!= COMPLETE) {
            Boolean allUser = taskInstance.getTask().getAllUser();
            Boolean completed;
            if (allUser) {
                completed = true;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = false;

                        break;
                    }
                }
            } else {
                completed = false;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = true;
                        break;
                    }
                }
            }

            if(completed==true){
                taskInstance.setTaskStatus(COMPLETE);
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
                        System.out.println("We added previous tasks "+taskActionRepository.findById(nextTask.getTaskActionId()).get().getTask().getTaskName());
                    }

                    List<TaskInstance> prevTaskInstances;
                    Boolean createNewTask = false;

                    for(Task prevTask: prevTasks){
                        TaskInstance taskInstanceTemp = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(prevTask, workflowInstance);
                        if(taskInstanceTemp.getTaskStatus() == COMPLETE){
                            createNewTask = true;
                        }else{
                            createNewTask = false;
                        }
                    }

                    if(createNewTask){
                        createNextTaskInstance(presentTaskUserInstance);
                    }


                }
            }

         */

        /*
        TaskInstance tempTask = taskInstanceRepository.findById(14).get();
        tempTask.setTaskStatus(PENDING);
        taskInstanceRepository.save(tempTask);
        TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(13).get();
        presentTaskUserInstance.setActionStatus("Reject");
        presentTaskUserInstance.setTaskUserStatus(COMPLETE);

        presentTaskUserInstance=taskUserInstanceRepository.save(presentTaskUserInstance);
        TaskInstance taskInstance = presentTaskUserInstance.getTaskInstance();
        if(taskInstance.getTaskStatus()!= COMPLETE) {
            Boolean allUser = taskInstance.getTask().getAllUser();
            Boolean completed;
            if (allUser) {
                completed = true;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = false;

                        break;
                    }
                }
            } else {
                completed = false;
                List<TaskUserInstance> taskUserInstances = taskUserInstanceRepository.findByTaskInstance(taskInstance);

                for (TaskUserInstance taskUserInstance : taskUserInstances) {
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = true;
                        break;
                    }
                }
            }

            if(completed==true){
                taskInstance.setTaskStatus(COMPLETE);
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
                        System.out.println("We added previous tasks "+taskActionRepository.findById(nextTask.getTaskActionId()).get().getTask().getTaskName());
                    }

                    List<TaskInstance> prevTaskInstances;
                    Boolean createNewTask = false;

                    for(Task prevTask: prevTasks){
                        TaskInstance taskInstanceTemp = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(prevTask, workflowInstance);
                        if(taskInstanceTemp.getTaskStatus() == COMPLETE){
                            createNewTask = true;
                        }else{
                            createNewTask = false;
                        }
                    }

                    if(createNewTask){
                        createNextTaskInstance(presentTaskUserInstance);
                    }


                }
            }



        }

         */

        /*
        TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(11).get();
        presentTaskUserInstance.setActionStatus("None");
        presentTaskUserInstance.setTaskUserStatus(COMPLETE);
        presentTaskUserInstance.setUploadFilePath("sample_file_upload");

        presentTaskUserInstance=taskUserInstanceRepository.save(presentTaskUserInstance);

        updateTaskInstanceAndCreateNextTask(presentTaskUserInstance);
        */


        TaskUserInstance presentTaskUserInstance = taskUserInstanceRepository.findById(13).get();
        presentTaskUserInstance.setActionStatus("Reject");
        presentTaskUserInstance.setTaskUserStatus(COMPLETE);
        presentTaskUserInstance.setUploadFilePath("sample_file_upload");
        presentTaskUserInstance.setComment("needs improvement");

        presentTaskUserInstance=taskUserInstanceRepository.save(presentTaskUserInstance);

        updateTaskInstanceAndCreateNextTask(presentTaskUserInstance);


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
                    if (taskUserInstance.getTaskUserStatus().compareTo(COMPLETE) != 0) {
                        completed = true;
                        break;
                    }
                }
            }

            if(completed==true){
                taskInstance.setTaskStatus(COMPLETE);
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
                        TaskInstance taskInstanceTemp = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(prevTask, workflowInstance);
                        System.out.println("Previous Task found is "+prevTask.getTaskName());
                        if(taskInstanceTemp.getTaskStatus() == COMPLETE){
                            createNewTask = true;
                        }else{
                            createNewTask = false;
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

                    for(Task nextTask: nextTasks1){
                        TaskInstance taskInstanceTemp = taskInstanceRepository
                                .findByTaskAndWorkflowInstance(nextTask, workflowInstance);
                        System.out.println("Previous' Next Task found is "+nextTask.getTaskName());
                        if(taskInstanceTemp.getTaskStatus() == COMPLETE){
                            nextTaskStatus = true;
                        }else{
                            nextTaskStatus = false;
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
                taskInstance.getWorkflowInstance().setWorkflowStatus(COMPLETE);
                taskInstanceRepository.save(taskInstance);
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

    public void createTaskUserInstance(TaskInstance taskInstance){

        Task task = taskInstance.getTask();
        User instantiator= taskInstance.getWorkflowInstance().getWorkflowInstantiator();
        List<TaskAction> taskActions = taskActionRepository.findByTask(task);
        Set<String> eligibleActionSet= new HashSet<>();

        for (TaskAction taskAction:
                taskActions) {
            eligibleActionSet.add(taskAction.getActionName());
        }

        String eligibleAction = String.join(",", eligibleActionSet);

        if(task.getTaskUserType().name().compareTo(TaskUserType.INITIATOR.name())==0){
            taskUserInstanceRepository.save(new TaskUserInstance(
                    taskInstance,
                    instantiator,
                    eligibleAction,
                    Timestamp.from(Instant.now()),
                    Timestamp.from(Instant.now())

            ));
        }else if(task.getTaskUserType().name().compareTo(TaskUserType.ROLE.name())==0){
            Set<User> users = new HashSet<>();
            Set<Role> roles = task.getTaskroles();
            for (Role role:
                    roles) {
                List<User> roleUsers = userRepository.findByRoles(role);
                users.addAll(roleUsers);
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


}
