package ru.home.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.courses.dto.CourseDTO;
import ru.home.courses.entity.Course;
import ru.home.courses.enums.CourseStatus;
import ru.home.courses.exception.CourseAlreadyExistsException;
import ru.home.courses.exception.CourseNotFoundException;
import ru.home.courses.mapper.CourseMapper;
import ru.home.courses.repository.CourseRepository;
import ru.home.courses.repository.VideoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final VideoRepository videoRepository;
    private final CourseMapper courseMapper;

    public List<CourseDTO> getAllCourses() {
        return courseMapper.toDTOs(courseRepository.findAll());

    }

    public CourseDTO getCourseById(Long id) {
        return courseMapper.toDTO(courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + id + " not found")));
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
                .orElseThrow(() -> new CourseNotFoundException("Course with name " + courseDTO.getName() + " not found"));
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setPrice(courseDTO.getPrice());
        course.setStatus(CourseStatus.valueOf(courseDTO.getStatus()));
        courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException("Course with id " + id + " not found");
        }
        courseRepository.deleteById(id);
    }
}
