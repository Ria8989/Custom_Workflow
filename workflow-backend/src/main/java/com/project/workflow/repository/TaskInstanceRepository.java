package com.project.workflow.repository;

import com.project.workflow.entity.Task;
import com.project.workflow.entity.TaskInstance;
import com.project.workflow.entity.Workflow;
import com.project.workflow.entity.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskInstanceRepository extends JpaRepository<TaskInstance, Integer> {

    List<TaskInstance> findByTaskAndWorkflowInstance(Task task, WorkflowInstance workflowInstance);

    List<TaskInstance> findByWorkflowInstance(WorkflowInstance workflowInstance);
}
