package icurriculum.domain.membermajor.repository;

import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MemberMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberMajorRepository extends JpaRepository<MemberMajor, Long> {

    @Query("select mm from MemberMajor mm join fetch mm.member m join fetch mm.department d where mm.member = :member")
    List<MemberMajor> findByMember(@Param("member") Member member);

}
