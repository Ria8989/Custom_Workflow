package com.project.workflow.repository;

import com.project.workflow.entity.User;
import com.project.workflow.entity.Workflow;
import com.project.workflow.entity.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Integer> {

    List<WorkflowInstance> findByWorkflow(Workflow workflow);

    List<WorkflowInstance> findByWorkflowInstantiator(User user);
}
