package icurriculum.domain.membermajor;

import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static icurriculum.domain.department.DepartmentName.컴퓨터공학과;
import static icurriculum.domain.member.RoleType.ROLE_USER;
import static icurriculum.domain.membermajor.MajorType.주전공;
import static org.assertj.core.api.Assertions.assertThat;

class MemberMajorTest {

    private Department department;
    private Member member;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .name(컴퓨터공학과)
                .build();

        member = Member.builder()
                .name("이승철")
                .joinYear(2019)
                .role(ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("주전공 일 때, isMain method가 True 반환")
    void memberMajorShouldBeMain_WhenMajorTypeIsMain() {
        // given
        MemberMajor memberMajor = MemberMajor.builder()
                .majorType(주전공)
                .department(department)
                .member(member)
                .build();

        // when & then
        assertThat(memberMajor.isMain()).isTrue();
    }

    @Test
    @DisplayName("주전공 아닐 때, isMain method가 False 반환")
    void memberMajorShouldNotBeMain_WhenMajorTypeIsNotMain() {
        // given
        MemberMajor memberMajor = MemberMajor.builder()
                .majorType(MajorType.복수전공)
                .department(department)
                .member(member)
                .build();

        // when & then
        assertThat(memberMajor.isMain()).isFalse();
    }
}

