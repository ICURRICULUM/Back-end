package icurriculum.domain.take.repository;

import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.take.Take;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TakeRepository extends JpaRepository<Take, Long> {

    @Query("SELECT t FROM Take t " +
        "LEFT JOIN FETCH t.course c " +
        "WHERE t.member = :member")
    List<Take> findByMember(@Param("member") Member member);

    @Query("SELECT t FROM Take t " +
        "LEFT JOIN FETCH t.course c " +
        "WHERE t.member = :member " +
        "AND t.majorType = :majorType")
    List<Take> findByMemberAndMajorType(
        @Param("member") Member member,
        @Param("majorType") MajorType majorType
    );

}
