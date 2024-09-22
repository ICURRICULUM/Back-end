package icurriculum.domain.membermajor.repository;

import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMajorRepository extends JpaRepository<MemberMajor, Long> {

    @Query("SELECT mm FROM MemberMajor mm " +
        "JOIN FETCH mm.member m " +
        "JOIN FETCH mm.department d " +
        "WHERE mm.member = :member")
    List<MemberMajor> findByMember(@Param("member") Member member);

    @Query("SELECT mm FROM MemberMajor mm " +
        "JOIN FETCH mm.member m " +
        "JOIN FETCH mm.department d " +
        "WHERE mm.member = :member " +
        "AND mm.majorType = :majorType")
    Optional<MemberMajor> findByMemberAndMajorType(
        @Param("member") Member member,
        @Param("majorType") MajorType majorType
    );

}
