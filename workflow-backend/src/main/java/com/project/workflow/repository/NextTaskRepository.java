package com.project.workflow.repository;

import com.project.workflow.entity.NextTask;
import com.project.workflow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NextTaskRepository extends JpaRepository<NextTask, Integer> {

    List<NextTask> findByTaskActionId(Integer taskActionId);

    List<NextTask> findByNextTask(Task task);
}
