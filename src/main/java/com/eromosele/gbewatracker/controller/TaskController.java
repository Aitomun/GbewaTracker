package com.eromosele.gbewatracker.controller;

import com.eromosele.gbewatracker.dto.taskDto.TaskRequestDto;
import com.eromosele.gbewatracker.dto.taskDto.TaskResponseDto;
import com.eromosele.gbewatracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(Authentication authentication) {
        return taskService.getAllTasks();
    }

    @PostMapping("/new-task")
    public TaskResponseDto createTask(Authentication authentication,
                                      @RequestBody TaskRequestDto taskRequestDto) {
        String email = authentication.getName();
        return taskService.createTask(email, taskRequestDto);
    }

}
