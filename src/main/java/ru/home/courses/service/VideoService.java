package ru.home.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.courses.dto.VideoDTO;
import ru.home.courses.entity.Course;
import ru.home.courses.entity.User;
import ru.home.courses.entity.Video;
import ru.home.courses.exception.NotFoundException;
import ru.home.courses.mapper.VideoMapper;
import ru.home.courses.repository.CourseRepository;
import ru.home.courses.repository.UserRepository;
import ru.home.courses.repository.VideoRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@Service
public class VideoService {

    @Value("${video.storage.path}")
    private String baseStoragePath;

    private final VideoRepository videoRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
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
                .orElseThrow(() -> new NotFoundException("Course not found"));
        video.setCourse(course);

        videoRepository.save(video);
    }

    @Transactional
    public Resource getVideoResource(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<Video> userVideos = user.getPurchasedCourses()
                .stream()
                .flatMap(course -> course.getVideos().stream())
                .toList();

        return createZipArchive(userVideos);
    }

    private Resource createZipArchive(List<Video> userVideos) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            for (Video video : userVideos) {
                Path videoPath = Paths.get(baseStoragePath).resolve(video.getPath()).normalize();

                if (!Files.exists(videoPath) || !Files.isReadable(videoPath)) {
                    throw new NotFoundException("Video not found or not accessible: " + video.getPath());
                }

                zipOutputStream.putNextEntry(new ZipEntry(video.getName()));
                Files.copy(videoPath, zipOutputStream);
                zipOutputStream.closeEntry();
            }

            return new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        } catch (IOException ex) {
            throw new RuntimeException("Error occurred while creating ZIP archive", ex);
        }
    }
}
