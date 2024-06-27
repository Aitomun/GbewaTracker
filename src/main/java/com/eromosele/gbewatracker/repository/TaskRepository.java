package com.eromosele.gbewatracker.repository;

import com.eromosele.gbewatracker.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
