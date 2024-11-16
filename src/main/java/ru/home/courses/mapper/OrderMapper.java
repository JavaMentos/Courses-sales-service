package ru.home.courses.mapper;

import org.mapstruct.Mapper;
import ru.home.courses.dto.OrderDTO;
import ru.home.courses.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);
}
