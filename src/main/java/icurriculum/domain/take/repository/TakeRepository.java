package icurriculum.domain.take.repository;


import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import icurriculum.domain.take.Take;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TakeRepository extends JpaRepository<Take, Long> {
    @Query("SELECT DISTINCT t FROM Take t " +
            "JOIN FETCH t.member m " +
            "LEFT JOIN FETCH t.course c " +
            "LEFT JOIN FETCH c.alternativeCourses ac " +
            "LEFT JOIN FETCH ac.department d " +
            "WHERE t.member = :member AND (ac IS NULL OR d = :department)")
    List<Take> findByMemberAndDepartment(@Param("member") Member member, @Param("department") Department department);

}
