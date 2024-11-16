package ru.home.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.courses.entity.Role;
import ru.home.courses.entity.User;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

}
