package ru.home.courses.dto;

import lombok.Getter;
import lombok.Setter;
import ru.home.courses.enums.OrderStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponseDTO {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
}
