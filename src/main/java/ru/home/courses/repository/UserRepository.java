package ru.home.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.home.courses.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.purchasedCourses c WHERE u.email = :email AND c.status = 'AVAILABLE'")
    Optional<User> findByEmailAndActiveCourses(@Param("email") String email);
}
