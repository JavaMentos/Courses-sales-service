package ru.home.courses.dto;

import lombok.Getter;
import lombok.Setter;
import ru.home.courses.entity.Role;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String role;
    private Set<Long> purchasedCourses;
}
