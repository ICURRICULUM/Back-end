package icurriculum.domain.membermajor.util;

import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MemberMajor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static icurriculum.domain.department.DepartmentName.컴퓨터공학과;
import static icurriculum.domain.member.RoleType.ROLE_USER;
import static icurriculum.domain.membermajor.MajorType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void findMainMajor_shouldReturnMainMajor() {
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
        MemberMajor mainMajor = MemberMajorUtils.findMainMajor(memberMajors);

        // then
        assertThat(mainMajor).isNotNull();
        assertThat(mainMajor.getMajorType()).isEqualTo(주전공);
    }

    @Test
    @DisplayName("주전공이 없는 경우 예외를 발생시켜야 한다.")
    void findMainMajor_shouldThrowExceptionWhenNoMainMajor() {
        // given
        List<MemberMajor> memberMajors = List.of(
                MemberMajor.builder()
                        .majorType(복수전공)
                        .member(testMember)
                        .department(testDepartment)
                        .build()
        );

        // when & then
        assertThatThrownBy(() -> MemberMajorUtils.findMainMajor(memberMajors))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("주전공을 제외한 다른 전공들을 올바르게 반환해야 한다.")
    void findOtherMajors_shouldReturnOtherMajors() {
        // given
        MemberMajor mainMajor = MemberMajor.builder()
                .majorType(주전공)
                .member(testMember)
                .department(testDepartment)
                .build();

        MemberMajor secondMajor = MemberMajor.builder()
                .majorType(복수전공)
                .member(testMember)
                .department(testDepartment)
                .build();

        List<MemberMajor> memberMajors = List.of(mainMajor, secondMajor);

        // when
        List<MemberMajor> otherMajors = MemberMajorUtils.findOtherMajors(memberMajors);

        // then
        assertThat(otherMajors).isNotNull();
        assertThat(otherMajors).hasSize(1);
        assertThat(otherMajors.get(0).getMajorType()).isNotEqualTo(주전공);
    }

    @Test
    @DisplayName("단일 전공인 경우 빈 리스트를 반환해야 한다.")
    void findOtherMajors_shouldReturnEmptyListForSingleMajor() {
        // given
        MemberMajor mainMajor = MemberMajor.builder()
                .majorType(주전공)
                .member(testMember)
                .department(testDepartment)
                .build();

        List<MemberMajor> memberMajors = List.of(mainMajor);

        // when
        List<MemberMajor> otherMajors = MemberMajorUtils.findOtherMajors(memberMajors);

        // then
        assertThat(otherMajors).isNotNull();
        assertThat(otherMajors).isEmpty();
    }
}
