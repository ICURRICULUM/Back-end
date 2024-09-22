package icurriculum.domain.course.repository;

import icurriculum.domain.course.Course;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCode(String code);

    @Query("SELECT c FROM Course c " +
        "WHERE c.code IN :codeSet")
    List<Course> findByCodeSet(@Param("codeSet") Set<String> codeSet);

}
