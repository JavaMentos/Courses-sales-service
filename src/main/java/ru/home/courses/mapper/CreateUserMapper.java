package ru.home.courses.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.home.courses.dto.CreateUser;
import ru.home.courses.entity.User;
import ru.home.courses.repository.CourseRepository;

@Mapper(componentModel = "spring", uses = {CourseRepository.class})
public interface CreateUserMapper {

//    @Mapping(target = "purchasedCourses", source = "purchasedCourses", qualifiedByName = "mapIdsToCourses")
    User toEntity(CreateUser createUser, @Context CourseRepository courseRepository);

//    @Named("mapIdsToCourses")
//    default Set<Course> mapIdsToCourses(Set<Long> courseIds, @Context CourseRepository courseRepository) {
//        if (courseIds == null) {
//            return null;
//        }
//        return courseIds.stream()
//                .map(courseRepository::findById)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toSet());
//    }
}
