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
public class TaskInfo {
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private LocalDate deadline;
    private PriorityLevel priorityLevel;
    private Status status;

}
