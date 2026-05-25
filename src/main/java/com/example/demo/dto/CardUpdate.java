package com.example.demo.dto;

import com.example.demo.model.Priority;
import com.example.demo.model.Status;
import lombok.Data;

@Data
public class CardUpdate {
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Long assigneeId;
    private java.time.LocalDateTime dueDate;
}