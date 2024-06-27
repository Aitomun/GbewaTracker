package com.eromosele.gbewatracker.dto.taskDto;

import com.eromosele.gbewatracker.enums.PriorityLevel;
import com.eromosele.gbewatracker.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestDto {

    private String title;
    private String description;
    private LocalDateTime creationDate;
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel;

    @Enumerated(EnumType.STRING)
    private Status status;

}
