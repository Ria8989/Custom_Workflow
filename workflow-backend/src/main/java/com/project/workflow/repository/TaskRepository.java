package com.project.workflow.repository;

import com.project.workflow.entity.Task;
import com.project.workflow.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Task findByTaskName(String taskName);

    Task findByTaskNameAndWorkflow(String taskName, Workflow workflow );

    List<Task> findByWorkflowAndIsFirstTask(Workflow workflow, Boolean isFirstTask);

    List<Task> findByWorkflow(Workflow workflow);

}
