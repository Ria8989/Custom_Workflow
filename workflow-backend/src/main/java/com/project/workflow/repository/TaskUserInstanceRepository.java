package com.project.workflow.repository;

import com.project.workflow.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskUserInstanceRepository extends JpaRepository<TaskUserInstance, Integer> {

    List<TaskUserInstance> findByUser(User user);

    List<TaskUserInstance> findByTaskInstance(TaskInstance taskInstance);

    TaskUserInstance findByUserAndTaskInstance(User user, TaskInstance taskInstance);
}
