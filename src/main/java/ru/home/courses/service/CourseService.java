package ru.home.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.courses.dto.CourseDTO;
import ru.home.courses.entity.Course;
import ru.home.courses.entity.User;
import ru.home.courses.enums.CourseStatus;
import ru.home.courses.exception.CourseAlreadyExistsException;
import ru.home.courses.exception.NotFoundException;
import ru.home.courses.mapper.CourseMapper;
import ru.home.courses.repository.CourseRepository;
import ru.home.courses.repository.UserRepository;
import ru.home.courses.repository.VideoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final VideoRepository videoRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;

    public List<CourseDTO> getAllCourses() {
        return courseMapper.toDTOs(courseRepository.findAll());

    }

    public CourseDTO getCourseById(Long id) {
        return courseMapper.toDTO(courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course with id " + id + " not found")));
    }

    public void createCourse(CourseDTO courseDTO) {
        if (courseRepository.findByName(courseDTO.getName()).isPresent()) {
            throw new CourseAlreadyExistsException("Course with name '" + courseDTO.getName() + "' already exists");
        }
        Course course = courseMapper.toEntity(courseDTO, videoRepository);
        courseRepository.save(course);
    }

    public void updateCourse(CourseDTO courseDTO) {
        Course course = courseRepository.findByName(courseDTO.getName())
                .orElseThrow(() -> new NotFoundException("Course with name " + courseDTO.getName() + " not found"));
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setPrice(courseDTO.getPrice());
        course.setStatus(CourseStatus.valueOf(courseDTO.getStatus()));
        courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new NotFoundException("Course with id " + id + " not found");
        }
        courseRepository.deleteById(id);
    }

    public List<CourseDTO> getPurchasedCourses(String email) {
        User user = userRepository.findByEmailAndActiveCourses(email)
                .orElseThrow(() -> new NotFoundException("no courses purchased"));

        return courseMapper.toDTOs(new ArrayList<>(user.getPurchasedCourses()));
    }
}
