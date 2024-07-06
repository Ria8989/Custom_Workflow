package com.project.workflow.repository;

import com.project.workflow.entity.Role;
import com.project.workflow.entity.User;
import com.project.workflow.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowRepository extends JpaRepository<Workflow, Integer> {

    List<Workflow> findByIsAllRole(Boolean isAllRole);

    List<Workflow> findByWorkflowroles(Role role);

    List<Workflow> findByCreator(User user);
}
