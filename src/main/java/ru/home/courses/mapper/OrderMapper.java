package ru.home.courses.mapper;

import org.mapstruct.Mapper;
import ru.home.courses.dto.OrderDTO;
import ru.home.courses.dto.OrderResponseDTO;
import ru.home.courses.entity.Order;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    OrderResponseDTO toResponseDTO(Order order);
    Order toEntity(OrderDTO orderDTO);

    default LocalDateTime map(LocalDateTime localDateTime) {
        return localDateTime;
    }

}
