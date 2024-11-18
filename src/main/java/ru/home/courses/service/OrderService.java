package ru.home.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.courses.dto.OrderDTO;
import ru.home.courses.dto.OrderResponse;
import ru.home.courses.entity.Course;
import ru.home.courses.entity.Order;
import ru.home.courses.entity.User;
import ru.home.courses.enums.OrderStatus;
import ru.home.courses.exception.NotFoundException;
import ru.home.courses.mapper.OrderMapper;
import ru.home.courses.repository.CourseRepository;
import ru.home.courses.repository.OrderRepository;
import ru.home.courses.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final OrderMapper orderMapper;


    public OrderResponse createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Course course = courseRepository.findById(orderDTO.getCourseId())
                .orElseThrow(() -> new NotFoundException("Course not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCourse(course);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponseDTO(savedOrder);
    }

    public boolean isCoursePurchasedByUser(Long courseId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getPurchasedCourses().stream()
                .anyMatch(course -> course.getId().equals(courseId));
    }

    public List<OrderDTO> getOrdersByUser(Long userId) {
        return orderRepository.findOrderByUserId(userId).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        order.setStatus(status);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponseDTO(savedOrder);
    }
}
