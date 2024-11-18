package ru.home.courses.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.home.courses.dto.CreateUser;
import ru.home.courses.entity.User;
import ru.home.courses.repository.CourseRepository;

@Mapper(componentModel = "spring", uses = {CourseRepository.class})
public interface CreateUserMapper {
    User toEntity(CreateUser createUser, @Context CourseRepository courseRepository);
}
