package ru.home.courses.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private long price;
    private String status;
    private List<Long> videos;
}
