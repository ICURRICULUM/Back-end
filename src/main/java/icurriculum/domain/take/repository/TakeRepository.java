package icurriculum.domain.take.repository;


import icurriculum.domain.member.Member;
import icurriculum.domain.take.Take;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TakeRepository extends JpaRepository<Take, Long> {
    @Query("SELECT t FROM Take t " +
            "JOIN FETCH t.member m " +
            "LEFT JOIN FETCH t.course c " +
            "WHERE t.member = :member")
    List<Take> findByMember(@Param("member") Member member);

}
