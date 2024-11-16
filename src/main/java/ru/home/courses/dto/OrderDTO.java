package ru.home.courses.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long courseId;
    private LocalDateTime orderDate;
    private String status;
}
