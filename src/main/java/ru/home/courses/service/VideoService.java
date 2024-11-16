package ru.home.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.courses.dto.VideoDTO;
import ru.home.courses.entity.Course;
import ru.home.courses.entity.Video;
import ru.home.courses.mapper.VideoMapper;
import ru.home.courses.repository.CourseRepository;
import ru.home.courses.repository.VideoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final CourseRepository courseRepository;
    private final VideoMapper videoMapper;

    public List<VideoDTO> getVideosByCourse(Long courseId) {
        return videoRepository.findAll().stream()
                .filter(video -> video.getCourse().getId().equals(courseId))
                .map(videoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void addVideoToCourse(Long courseId, VideoDTO videoDTO) {
        Video video = videoMapper.toEntity(videoDTO);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        video.setCourse(course);

        videoRepository.save(video);
    }
}
