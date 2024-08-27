package icurriculum.domain.membermajor.repository;

import icurriculum.data.컴퓨터공학과DataInitializer;
import icurriculum.domain.department.Department;
import icurriculum.domain.department.repository.DepartmentRepository;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.RoleType;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.MajorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(컴퓨터공학과DataInitializer.class)
class MemberMajorRepositoryTest {

    @Autowired
    private MemberMajorRepository memberMajorRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private 컴퓨터공학과DataInitializer 컴퓨터공학과DataInitializer;

    Member member;

    @BeforeEach
    void setUp() {
        Department department = 컴퓨터공학과DataInitializer.getDepartmentData();
        member = 컴퓨터공학과DataInitializer.getMemberData();
        MemberMajor memberMajor = 컴퓨터공학과DataInitializer.getMemberMajorData(member, department);

        departmentRepository.save(department);
        memberRepository.save(member);
        memberMajorRepository.save(memberMajor);
    }

    @Test
    @DisplayName("memberMajor가 있는 맴버, findByMember method 실행, MemberMajor List 정상 반환")
    void findByMember_shouldReturnMemberMajorsForGivenMember() {
        // when
        List<MemberMajor> memberMajors = memberMajorRepository.findByMember(member);

        // then
        assertThat(memberMajors).isNotNull();
        assertThat(memberMajors).hasSize(1);
        assertThat(memberMajors.get(0).getMember()).isEqualTo(member);
        assertThat(memberMajors.get(0).getMajorType()).isEqualTo(MajorType.주전공);
    }

    @Test
    @DisplayName("memberMajor가 없는 맴버, findByMember method 실행, 비어있는 MemberMajor List 반환")
    void findByMember_shouldReturnEmptyListForNonExistingMember() {
        // given
        Member nonExistingMember = Member.builder()
                .name("nonExistingMember")
                .joinYear(2021)
                .role(RoleType.ROLE_USER)
                .build();
        memberRepository.save(nonExistingMember);

        // when
        List<MemberMajor> memberMajors = memberMajorRepository.findByMember(nonExistingMember);

        // then
        assertThat(memberMajors).isNotNull();
        assertThat(memberMajors).isEmpty();
    }
}
