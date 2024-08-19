package icurriculum.domain.course.repository;

import icurriculum.domain.course.Course;
import icurriculum.domain.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);

    @Query("SELECT DISTINCT c FROM Course c " +
            "LEFT JOIN FETCH c.alternativeCourses ac " +
            "LEFT JOIN FETCH ac.department d " +
            "WHERE c.code IN :codes AND (ac IS NULL OR d = :department)")
    List<Course> findByCodesAndDepartment(@Param("codes") Set<String> codes, @Param("department") Department department);

}
