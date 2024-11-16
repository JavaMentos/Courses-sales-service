package ru.home.courses.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUser {
      private String email;
      private String password;
      private String name;
      private String phoneNumber;
      private LocalDate dateOfBirth;
}
