package ru.home.courses.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.home.courses.dto.CourseDTO;
import ru.home.courses.service.CourseService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @Timed(value = "courses.getAll", description = "Time taken to get all courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    @Timed(value = "courses.getById", description = "Time taken to get course by ID")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    @Timed(value = "courses.create", description = "Time taken to create a course")
    public ResponseEntity<String> createCourse(@RequestBody CourseDTO courseDTO) {
        courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Course created successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateCourse(@RequestBody CourseDTO courseDTO) {
        courseService.updateCourse(courseDTO);
        return ResponseEntity.ok("Course updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully");
    }

    @GetMapping("/purchased")
    public ResponseEntity<List<CourseDTO>> getPurchasedCourses(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(courseService.getPurchasedCourses(email));
    }
}
