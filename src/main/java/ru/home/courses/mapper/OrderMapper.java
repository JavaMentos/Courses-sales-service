package ru.home.courses.mapper;

import org.mapstruct.Mapper;
import ru.home.courses.dto.OrderDTO;
import ru.home.courses.dto.OrderResponse;
import ru.home.courses.entity.Order;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    OrderResponse toResponseDTO(Order order);
    Order toEntity(OrderDTO orderDTO);

    default LocalDateTime map(LocalDateTime localDateTime) {
        return localDateTime;
    }

}
