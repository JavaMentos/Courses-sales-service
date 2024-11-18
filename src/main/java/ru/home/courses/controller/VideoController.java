package ru.home.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.home.courses.dto.VideoDTO;
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
    public ResponseEntity<List<VideoDTO>> getVideosByCourse(
            @PathVariable Long courseId, Authentication authentication) {

        String email = authentication.getName();
        if (!orderService.isCoursePurchasedByUser(courseId, email)) {
            throw new RuntimeException("You do not have access to this course");
        }

        return ResponseEntity.ok(videoService.getVideosByCourse(courseId));
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<String> addVideoToCourse(@PathVariable Long courseId, @RequestBody VideoDTO videoDTO) {
        videoService.addVideoToCourse(courseId, videoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Video added successfully");
    }
}
