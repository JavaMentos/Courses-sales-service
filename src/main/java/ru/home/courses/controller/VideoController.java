package ru.home.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.home.courses.dto.VideoDTO;
import ru.home.courses.exception.NotFoundException;
import ru.home.courses.service.OrderService;
import ru.home.courses.service.VideoService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;
    private final OrderService orderService;

    @GetMapping("/{courseId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VideoDTO>> getVideosByCourse(
            @PathVariable Long courseId, Authentication authentication) {

        String email = authentication.getName();
        String role = authentication.getAuthorities().stream().findFirst().get().toString();
        if (!orderService.isCoursePurchasedByUser(courseId, email) && !role.equalsIgnoreCase("role_admin")) {
            throw new AccessDeniedException("You do not have access to this course");
        }

        List<VideoDTO> videosByCourse = videoService.getVideosByCourse(courseId);
        if (videosByCourse.isEmpty()) {
            throw new NotFoundException("Video not found");
        }

        return ResponseEntity.ok(videoService.getVideosByCourse(courseId));
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<String> addVideoToCourse(@PathVariable Long courseId, @RequestBody VideoDTO videoDTO) {
        videoService.addVideoToCourse(courseId, videoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Video added successfully");
    }

    @GetMapping("/download/{videoId}")
    public ResponseEntity<Resource> downloadVideo(
            @PathVariable Long videoId, Authentication authentication) {

        String userName = authentication.getName();
        Resource resource = videoService.getVideoResource(userName, videoId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"videos.zip\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
