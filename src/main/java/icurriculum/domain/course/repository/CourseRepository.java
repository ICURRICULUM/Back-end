package icurriculum.domain.course.repository;

import icurriculum.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);

    @Query("SELECT c FROM Course c " +
            "WHERE c.code IN :codes")
    List<Course> findByCodes(@Param("codes") Set<String> codes);

}
