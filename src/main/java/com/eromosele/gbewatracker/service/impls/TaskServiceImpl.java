package com.eromosele.gbewatracker.service.impls;

import com.eromosele.gbewatracker.dto.taskDto.TaskInfo;
import com.eromosele.gbewatracker.dto.taskDto.TaskRequestDto;
import com.eromosele.gbewatracker.dto.taskDto.TaskResponseDto;
import com.eromosele.gbewatracker.entity.TaskEntity;
import com.eromosele.gbewatracker.entity.UserEntity;
import com.eromosele.gbewatracker.enums.Status;
import com.eromosele.gbewatracker.repository.TaskRepository;
import com.eromosele.gbewatracker.repository.UserRepository;
import com.eromosele.gbewatracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private  final UserRepository userRepository;



    @Override
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();

        // Convert the list of TaskEntity to TaskResponseDto
        List<TaskResponseDto> responseDtos = taskEntities.stream()
                .map(task -> TaskResponseDto.builder()
                        .responseCode("000")
                        .responseMessage("Success")
                        .taskInfo(TaskInfo.builder()
                                .title(task.getTitle())
                                .description(task.getDescription())
                                .creationDate(task.getCreationDate())
                                .deadline(task.getDeadline())
                                .priorityLevel(task.getPriorityLevel())
                                .status(task.getStatus())
                                .build())
                        .build())
                .collect(Collectors.toList());

        // Return the response entity with the list of TaskResponseDto
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }


    @Override
    public TaskResponseDto createTask(String email, TaskRequestDto taskRequestDto) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            throw new RuntimeException();
            //we can add a redirect to the login page
        }

        UserEntity user = optionalUser.get();

        taskRequestDto.setStatus(Status.PENDING);

        TaskEntity task = TaskEntity.builder()
                .title(taskRequestDto.getTitle())
                .description(taskRequestDto.getDescription())
                .creationDate(taskRequestDto.getCreationDate())
                .deadline(taskRequestDto.getDeadline())
                .priorityLevel(taskRequestDto.getPriorityLevel())
                .status(taskRequestDto.getStatus())
                .user(user)
                .build();
        taskRepository.save(task);

        return TaskResponseDto.builder()
                .responseCode("001")
                .responseMessage("Task created successfully")
                .taskInfo(TaskInfo.builder()
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .deadline(task.getDeadline())
                        .priorityLevel(task.getPriorityLevel())
                        .status(task.getStatus())
                        .creationDate(task.getCreationDate())
                        .build())
                .build();

    }
}

