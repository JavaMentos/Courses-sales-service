package ru.home.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.courses.entity.Order;
import ru.home.courses.enums.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByUserId(long userId);
    List<Order> findOrdersByStatus(OrderStatus status);

}
