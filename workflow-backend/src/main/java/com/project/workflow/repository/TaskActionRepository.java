package com.project.workflow.repository;

import com.project.workflow.entity.TaskAction;
import com.project.workflow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskActionRepository extends JpaRepository<TaskAction, Integer> {
    TaskAction findByActionNameAndTask(String actionName, Task task);

    List<TaskAction> findByTask(Task task);
}
