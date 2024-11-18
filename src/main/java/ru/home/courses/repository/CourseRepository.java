package ru.home.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.courses.entity.Course;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);
}
