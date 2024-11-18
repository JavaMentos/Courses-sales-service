package ru.home.courses.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.home.courses.dto.UserDTO;
import ru.home.courses.entity.Course;
import ru.home.courses.entity.User;
import ru.home.courses.repository.CourseRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CourseRepository.class})
public interface UserMapper {
    @Mapping(target = "purchasedCourses", source = "purchasedCourses", qualifiedByName = "mapCoursesToIds")
    UserDTO toDTO(User user);

    @Mapping(target = "purchasedCourses", source = "purchasedCourses", qualifiedByName = "mapIdsToCourses")
    User toEntity(UserDTO userDTO, @Context CourseRepository courseRepository);

    @Named("mapCoursesToIds")
    default Set<Long> mapCoursesToIds(Set<Course> courses) {
        if (courses == null) {
            return null;
        }
        return courses.stream()
                .map(Course::getId)
                .collect(Collectors.toSet());
    }

    @Named("mapIdsToCourses")
    default Set<Course> mapIdsToCourses(Set<Long> courseIds, @Context CourseRepository courseRepository) {
        if (courseIds == null) {
            return null;
        }
        return courseIds.stream()
                .map(courseRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
