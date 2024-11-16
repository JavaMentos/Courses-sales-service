package ru.home.courses.mapper;

import org.mapstruct.Mapper;
import ru.home.courses.dto.VideoDTO;
import ru.home.courses.entity.Video;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    VideoDTO toDTO(Video video);
    Video toEntity(VideoDTO videoDTO);
}
