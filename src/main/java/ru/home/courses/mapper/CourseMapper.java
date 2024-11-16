package ru.home.courses.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.home.courses.dto.CourseDTO;
import ru.home.courses.entity.Course;
import ru.home.courses.entity.Video;
import ru.home.courses.repository.VideoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "videos", source = "videos", qualifiedByName = "mapVideosToIds")
    CourseDTO toDTO(Course course);

    @Mapping(target = "videos", source = "videos", qualifiedByName = "mapIdsToVideos")
    Course toEntity(CourseDTO courseDTO, @Context VideoRepository videoRepository);

    List<CourseDTO> toDTOs(List<Course> courses);

    @Named("mapVideosToIds")
    default List<Long> mapVideosToIds(List<Video> videos) {
        if (videos == null) {
            return null;
        }
        return videos.stream()
                .map(Video::getId) // Извлекаем ID каждого видео
                .collect(Collectors.toList());
    }

    @Named("mapIdsToVideos")
    default List<Video> mapIdsToVideos(List<Long> videoIds, @Context VideoRepository videoRepository) {
        if (videoIds == null) {
            return null;
        }
        return videoIds.stream()
                .map(videoRepository::findById) // Ищем видео по ID
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
