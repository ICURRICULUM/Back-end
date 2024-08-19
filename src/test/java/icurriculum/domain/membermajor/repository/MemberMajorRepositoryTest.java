package icurriculum.domain.membermajor.repository;

import icurriculum.DataInitializer;
import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MemberMajor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberMajorRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberMajorRepository memberMajorRepository;
    @Autowired
    DataInitializer dataInitializer;

    @Test
    @DisplayName("회원전공이_1개일때_회원별_전공상태리스트_조회_테스트")
    public void findByMember() throws Exception {
        // given
        Member member = memberRepository.findById(dataInitializer.getTestMemberId()).get();
        Department department = dataInitializer.getTestDepartmentOnlyData();
        MemberMajor actualMemberMajor = dataInitializer.getTestMemberMajorOnlyData(member, department);

        // when
        MemberMajor expectedMemberMajor = memberMajorRepository.findByMember(member).get(0);

        // then
        assertThat(actualMemberMajor.getMajorType()).isEqualTo(expectedMemberMajor.getMajorType());
        assertThat(actualMemberMajor.getDepartment().getName()).isEqualTo(expectedMemberMajor.getDepartment().getName());
    }


}