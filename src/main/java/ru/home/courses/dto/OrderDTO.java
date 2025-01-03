package ru.home.courses.dto;

import lombok.Getter;
import lombok.Setter;

import ru.home.courses.enums.OrderStatus;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long courseId;
    private OrderStatus status;
}
