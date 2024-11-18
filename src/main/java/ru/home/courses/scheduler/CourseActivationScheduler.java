package ru.home.courses.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.home.courses.entity.Course;
import ru.home.courses.entity.Order;
import ru.home.courses.entity.User;
import ru.home.courses.enums.OrderStatus;
import ru.home.courses.repository.OrderRepository;
import ru.home.courses.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseActivationScheduler {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Scheduled(initialDelayString = ("${scheduling.initialDelay}"), fixedDelayString = "${scheduling.fixedDelay}")
    @Transactional
    public void activatePurchasedCourses() {
        List<Order> completedOrders = orderRepository.findOrdersByStatus(OrderStatus.COMPLETED);

        for (Order order : completedOrders) {
            User user = order.getUser();
            Course course = order.getCourse();

            if (!user.getPurchasedCourses().contains(course)) {
                user.getPurchasedCourses().add(course);
                userRepository.save(user);
            }
        }
    }
}
