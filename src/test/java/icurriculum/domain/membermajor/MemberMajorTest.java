package icurriculum.domain.membermajor;

import static org.assertj.core.api.Assertions.assertThat;

import icurriculum.domain.department.Department;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberMajorTest {

    private Member testMember;
    private Department testDepartment;

    @BeforeEach
    void setUp() {
        testMember = Member.builder()
            .name("이승철")
            .joinYear(19)
            .role(RoleType.ROLE_USER)
            .build();

        testDepartment = Department.builder()
            .name(DepartmentName.컴퓨터공학과)
            .build();
    }

    @Test
    @DisplayName("MemberMajor 객체가 정상적으로 생성되는지 테스트")
    void memberMajor_객체_생성_테스트() {
        // given
        MemberMajor memberMajor = MemberMajor.builder()
            .majorType(MajorType.주전공)
            .department(testDepartment)
            .member(testMember)
            .build();

        // when & then
        assertThat(memberMajor.getMajorType()).isEqualTo(MajorType.주전공);
        assertThat(memberMajor.getDepartment()).isEqualTo(testDepartment);
        assertThat(memberMajor.getMember()).isEqualTo(testMember);
    }

}
