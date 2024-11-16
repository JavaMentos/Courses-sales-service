package ru.home.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.courses.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
