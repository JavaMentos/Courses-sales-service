package ru.home.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.courses.dto.OrderDTO;
import ru.home.courses.entity.Course;
import ru.home.courses.entity.Order;
import ru.home.courses.entity.User;
import ru.home.courses.enums.OrderStatus;
import ru.home.courses.mapper.OrderMapper;
import ru.home.courses.repository.CourseRepository;
import ru.home.courses.repository.OrderRepository;
import ru.home.courses.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final OrderMapper orderMapper;


    public void createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);

        order.setUser(userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        order.setCourse(courseRepository.findById(orderDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found")));

        orderRepository.save(order);
    }

    public List<OrderDTO> getOrdersByUser(Long userId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getUser().getId().equals(userId))
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
