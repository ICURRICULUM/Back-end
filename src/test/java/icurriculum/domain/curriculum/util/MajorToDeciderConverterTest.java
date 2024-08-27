package icurriculum.domain.curriculum.util;

import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MemberMajor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static icurriculum.domain.department.DepartmentName.*;
import static icurriculum.domain.member.RoleType.ROLE_USER;
import static icurriculum.domain.membermajor.MajorType.*;
import static org.assertj.core.api.Assertions.assertThat;

class MajorToDeciderConverterTest {

    private Member testMember;
    private Department testDepartment_컴공;
    private Department testDepartment_전기;

    private Department testDepartment_기계;

    @BeforeEach
    void setUp() {
        testDepartment_컴공 = Department.builder()
                .name(컴퓨터공학과)
                .build();

        testDepartment_전기 = Department.builder()
                .name(전기공학과)
                .build();

        testDepartment_기계 = Department.builder()
                .name(기계공학과)
                .build();

        testMember = Member.builder()
                .name("이승철")
                .joinYear(2019)
                .role(ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("MemberMajor를 CurriculumDecider로 올바르게 변환해야 한다.")
    void toDecider_shouldReturnCorrectCurriculumDecider() {
        // given
        MemberMajor memberMajor = MemberMajor.builder()
                .majorType(주전공)
                .department(testDepartment_컴공)
                .member(testMember)
                .build();

        // when
        CurriculumDecider decider = MajorToDeciderConverter.toDecider(memberMajor);

        // then
        assertThat(decider).isNotNull();
        assertThat(decider.majorType()).isEqualTo(memberMajor.getMajorType());
        assertThat(decider.department()).isEqualTo(testDepartment_컴공);
        assertThat(decider.joinYear()).isEqualTo(testMember.getJoinYear());
    }

    @Test
    @DisplayName("MemberMajor 리스트를 CurriculumDecider 리스트로 올바르게 변환해야 한다.")
    void toDeciders_shouldReturnCorrectListOfCurriculumDeciders() {
        // given
        List<MemberMajor> memberMajors = List.of(
                MemberMajor.builder()
                        .majorType(복수전공)
                        .department(testDepartment_전기)
                        .member(testMember)
                        .build(),
                MemberMajor.builder()
                        .majorType(부전공)
                        .department(testDepartment_기계)
                        .member(testMember)
                        .build()
        );

        // when
        List<CurriculumDecider> deciders = MajorToDeciderConverter.toDeciders(memberMajors);

        // then
        assertThat(deciders).isNotNull();
        assertThat(deciders).hasSize(2);

        assertThat(deciders.get(0).majorType()).isEqualTo(memberMajors.get(0).getMajorType());
        assertThat(deciders.get(0).department()).isEqualTo(memberMajors.get(0).getDepartment());
        assertThat(deciders.get(0).joinYear()).isEqualTo(memberMajors.get(0).getMember().getJoinYear());

        assertThat(deciders.get(1).majorType()).isEqualTo(memberMajors.get(1).getMajorType());
        assertThat(deciders.get(1).department()).isEqualTo(memberMajors.get(1).getDepartment());
        assertThat(deciders.get(1).joinYear()).isEqualTo(memberMajors.get(1).getMember().getJoinYear());
    }
}
