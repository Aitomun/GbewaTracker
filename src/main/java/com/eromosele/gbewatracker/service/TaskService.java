package com.eromosele.gbewatracker.service;

import com.eromosele.gbewatracker.dto.taskDto.TaskRequestDto;
import com.eromosele.gbewatracker.dto.taskDto.TaskResponseDto;
import com.eromosele.gbewatracker.entity.TaskEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {

    ResponseEntity<List<TaskResponseDto>> getAllTasks();

    TaskResponseDto createTask(String email, TaskRequestDto taskRequestDto);
}
