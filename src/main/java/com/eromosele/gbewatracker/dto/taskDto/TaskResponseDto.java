package com.eromosele.gbewatracker.dto.taskDto;

import com.eromosele.gbewatracker.enums.PriorityLevel;
import com.eromosele.gbewatracker.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDto {

    private String responseCode;
    private String responseMessage;
    private TaskInfo taskInfo;


    public TaskResponseDto(String title, String description, LocalDateTime creationDate, LocalDate deadline, PriorityLevel priorityLevel, Status status) {
    }
}
