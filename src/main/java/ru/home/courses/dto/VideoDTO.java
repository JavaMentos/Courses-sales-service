package ru.home.courses.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDTO {
    private Long id;
    private String name;
    private String path;
    private Long courseId;
    private String description;
}
