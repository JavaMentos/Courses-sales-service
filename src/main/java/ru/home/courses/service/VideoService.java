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

    public VideoDTO getVideoById(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException("video not found"));

        return videoMapper.toDTO(video);
    }

    public void addVideoToCourse(Long courseId, VideoDTO videoDTO) {
        Video video = videoMapper.toEntity(videoDTO);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found"));
        video.setCourse(course);

        videoRepository.save(video);
    }

    @Transactional
    public Resource getVideoResource(String email, Long videoId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Video video;

        if (user.getRole().getName().equals("ADMIN")) {
             video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new NotFoundException("video not found"));
        } else {
            video = user.getPurchasedCourses()
                    .stream()
                    .flatMap(course -> course.getVideos().stream())
                    .filter(v -> v.getId().equals(videoId))
                    .findAny()
                    .orElseThrow(() -> new NotFoundException("video not found"));
        }
            //
//        List<Video> userVideos = user.getPurchasedCourses()
//                .stream()
//                .flatMap(course -> course.getVideos().stream())
//                .toList();

        return createZipArchive(video);
    }

    private Resource createZipArchive(Video video) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

//            for (Video video : userVideos) {
                Path videoPath = Paths.get(baseStoragePath, video.getPath(), video.getName()).normalize();

                if (!Files.exists(videoPath) || !Files.isReadable(videoPath)) {
                    throw new NotFoundException("Video not found or not accessible: " + videoPath.toAbsolutePath());
                }

                zipOutputStream.putNextEntry(new ZipEntry(video.getName()));

                try (var fileInputStream = Files.newInputStream(videoPath)) {
                    fileInputStream.transferTo(zipOutputStream);
                }

                zipOutputStream.closeEntry();
//            }

            zipOutputStream.finish();

            return new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        } catch (IOException ex) {
            throw new RuntimeException("Error occurred while creating ZIP archive", ex);
        }
    }

//    private Resource createZipArchive(List<Video> userVideos) {
//        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
//
//            for (Video video : userVideos) {
//                Path videoPath = Paths.get(baseStoragePath, video.getPath(), video.getName()).normalize();
//
//                if (!Files.exists(videoPath) || !Files.isReadable(videoPath)) {
//                    throw new NotFoundException("Video not found or not accessible: " + videoPath.toAbsolutePath());
//                }
//
//                zipOutputStream.putNextEntry(new ZipEntry(video.getName()));
//
//                try (var fileInputStream = Files.newInputStream(videoPath)) {
//                    fileInputStream.transferTo(zipOutputStream);
//                }
//
//                zipOutputStream.closeEntry();
//            }
//
//            zipOutputStream.finish();
//
//            return new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
//        } catch (IOException ex) {
//            throw new RuntimeException("Error occurred while creating ZIP archive", ex);
//        }
//    }
}
