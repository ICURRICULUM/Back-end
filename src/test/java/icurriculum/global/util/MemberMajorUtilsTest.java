package icurriculum.global.util;

import static icurriculum.domain.department.DepartmentName.컴퓨터공학과;
import static icurriculum.domain.member.RoleType.ROLE_USER;
import static icurriculum.domain.membermajor.MajorType.복수전공;
import static icurriculum.domain.membermajor.MajorType.주전공;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MemberMajor;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberMajorUtilsTest {

    private Member testMember;
    private Department testDepartment;

    @BeforeEach
    void setUp() {
        testDepartment = Department.builder()
            .name(컴퓨터공학과)
            .build();

        testMember = Member.builder()
            .name("이승철")
            .joinYear(2019)
            .role(ROLE_USER)
            .build();
    }

    @Test
    @DisplayName("주전공을 가진 MemberMajor를 올바르게 반환해야 한다.")
    void extractMemberMajorByMajorType_shouldReturnMainMajor() {
        // given
        List<MemberMajor> memberMajors = List.of(
            MemberMajor.builder()
                .majorType(주전공)
                .member(testMember)
                .department(testDepartment)
                .build(),
            MemberMajor.builder()
                .majorType(복수전공)
                .member(testMember)
                .department(testDepartment)
                .build()
        );

        // when
        MemberMajor mainMajor = MemberMajorUtils.extractMemberMajorByMajorType(
            memberMajors,
            주전공
        );

        // then
        assertThat(mainMajor).isNotNull();
        assertThat(mainMajor.getMajorType()).isEqualTo(주전공);
    }

    @Test
    @DisplayName("없는 경우 예외를 발생시켜야 한다.")
    void extractMemberMajorByMajorType_shouldThrowExceptionWhenNoMajor() {
        // given
        List<MemberMajor> memberMajors = List.of(
            MemberMajor.builder()
                .majorType(복수전공)
                .member(testMember)
                .department(testDepartment)
                .build()
        );

        // when & then
        assertThatThrownBy(() -> MemberMajorUtils.extractMemberMajorByMajorType(
            memberMajors,
            주전공
        ))
            .isInstanceOf(RuntimeException.class);
    }

}
