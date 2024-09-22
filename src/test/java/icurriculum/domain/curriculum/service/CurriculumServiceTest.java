package icurriculum.domain.curriculum.service;

import static icurriculum.domain.department.DepartmentName.컴퓨터공학과;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.global.response.exception.GeneralException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurriculumServiceTest {

    @Mock
    private CurriculumRepository curriculumRepository;

    @InjectMocks
    private CurriculumService curriculumService;

    private MemberMajor testMemberMajor;
    private Curriculum testCurriculum;

    @BeforeEach
    void setUp() {
        Member testMember = Member.builder().name("이승철").joinYear(19).build();
        Department testDepartment = Department.builder().name(컴퓨터공학과).build();

        testMemberMajor = MemberMajor.builder()
            .majorType(MajorType.주전공)
            .department(testDepartment)
            .member(testMember)
            .build();

        CurriculumDecider decider = new CurriculumDecider(
            testMemberMajor.getMajorType(),
            testMemberMajor.getDepartment().getName(),
            testMemberMajor.getMember().getJoinYear()
        );

        testCurriculum = Curriculum.builder()
            .decider(decider)
            .build();
    }

    @Test
    @DisplayName("MemberMajor로 Curriculum 조회 성공 테스트")
    void getCurriculumByMemberMajor_성공() {
        // given
        CurriculumDecider decider = new CurriculumDecider(
            testMemberMajor.getMajorType(),
            testMemberMajor.getDepartment().getName(),
            testMemberMajor.getMember().getJoinYear()
        );

        when(curriculumRepository.findByDecider(decider))
            .thenReturn(Optional.of(testCurriculum));

        // when
        Curriculum foundCurriculum = curriculumService.getCurriculumByMemberMajor(testMemberMajor);

        // then
        assertThat(foundCurriculum).isEqualTo(testCurriculum);
    }

    @Test
    @DisplayName("MemberMajor로 Curriculum 조회 실패 테스트 - Curriculum이 없는 경우")
    void getCurriculumByMemberMajor_실패_없는_경우() {
        // given
        CurriculumDecider decider = new CurriculumDecider(
            testMemberMajor.getMajorType(),
            testMemberMajor.getDepartment().getName(),
            testMemberMajor.getMember().getJoinYear()
        );

        when(curriculumRepository.findByDecider(decider))
            .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> curriculumService.getCurriculumByMemberMajor(testMemberMajor))
            .isInstanceOf(GeneralException.class);
    }
}
