package ru.home.courses.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Set<Long> purchasedCourses;
}
