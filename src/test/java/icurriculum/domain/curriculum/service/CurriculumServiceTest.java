package icurriculum.domain.curriculum.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.Department;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.global.response.exception.GeneralException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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

    private MemberMajor memberMajor;
    private CurriculumDecider decider;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
            .joinYear(2021)
            .build();

        Department department = Department.builder()
            .name(DepartmentName.컴퓨터공학과)
            .build();

        memberMajor = MemberMajor.builder()
            .member(member)
            .department(department)
            .majorType(MajorType.주전공)
            .build();

        decider = CurriculumDecider.builder()
            .majorType(MajorType.주전공)
            .departmentName(department.getName())
            .joinYear(2021)
            .build();
    }

    @Test
    void 커리큘럼_조회_성공() {
        // given
        Curriculum curriculum = mock(Curriculum.class);
        when(curriculumRepository.findByDecider(decider))
            .thenReturn(Optional.of(curriculum));

        // when
        curriculumService.getCurriculumByMemberMajor(memberMajor);

        // then
        verify(curriculumRepository, times(1))
            .findByDecider(any(CurriculumDecider.class));
        verify(curriculum, times(1))
            .validate();
    }

    @Test
    void 커리큘럼_조회_실패() {
        // given
        when(curriculumRepository.findByDecider(any(CurriculumDecider.class)))
            .thenReturn(Optional.empty());

        // when & then
        GeneralException exception = assertThrows(
            GeneralException.class,
            () -> curriculumService.getCurriculumByMemberMajor(memberMajor)
        );

        verify(curriculumRepository, times(1))
            .findByDecider(any(CurriculumDecider.class));
    }
}
