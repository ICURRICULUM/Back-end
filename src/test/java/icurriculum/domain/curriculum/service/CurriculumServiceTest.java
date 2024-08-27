package icurriculum.domain.curriculum.service;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.Department;
import icurriculum.domain.membermajor.MajorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static icurriculum.domain.department.DepartmentName.전기공학과;
import static icurriculum.domain.department.DepartmentName.컴퓨터공학과;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurriculumServiceTest {

    @Mock
    private CurriculumRepository curriculumRepository;

    @InjectMocks
    private CurriculumService curriculumService;

    private CurriculumDecider mainDecider;
    private CurriculumDecider otherDecider;
    private Curriculum curriculum;

    private Department department_컴퓨터공학과;
    private Department department_전기공학과;

    @BeforeEach
    void setUp() {
        department_컴퓨터공학과 = Department.builder().name(컴퓨터공학과).build();
        department_전기공학과 = Department.builder().name(전기공학과).build();

        mainDecider = new CurriculumDecider(MajorType.주전공, department_컴퓨터공학과, 2020);
        otherDecider = new CurriculumDecider(MajorType.복수전공, department_전기공학과, 2020);

        curriculum = Curriculum.builder()
                .decider(mainDecider)
                .coreJson(null)
                .swAiJson(null)
                .creativityJson(null)
                .requiredCreditJson(null)
                .curriculumCodesJson(null)
                .alternativeCourseJson(null)
                .build();
    }

    @Test
    @DisplayName("주전공으로 Curriculum 조회 성공")
    void findCurriculumByDeciderOnlyMain_ShouldReturnCurriculum() {
        // given
        when(curriculumRepository.findByDecider(mainDecider)).thenReturn(Optional.of(curriculum));

        // when
        Curriculum foundCurriculum = curriculumService.findCurriculumByDeciderOnlyMain(mainDecider);

        // then
        assertThat(foundCurriculum).isNotNull();
        assertThat(foundCurriculum).isEqualTo(curriculum);
    }

    @Test
    @DisplayName("주전공이 아닌 경우 Curriculum 조회 시 예외 발생")
    void findCurriculumByDeciderOnlyMain_ShouldThrowExceptionForNonMain() {
        // when & then
        assertThatThrownBy(() -> curriculumService.findCurriculumByDeciderOnlyMain(otherDecider))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("주전공이 포함된 Decider 리스트로 Curriculum 조회 시 예외 발생")
    void findCurriculumsByDecidersOnlyOthers_ShouldThrowExceptionForMainMajorInList() {
        // given
        List<CurriculumDecider> deciders = List.of(mainDecider, otherDecider);

        // when & then
        assertThatThrownBy(() -> curriculumService.findCurriculumsByDecidersOnlyOthers(deciders))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("동일한 학과가 포함된 Decider 리스트로 Curriculum 조회 시 예외 발생")
    void findCurriculumsByDecidersOnlyOthers_ShouldThrowExceptionForSameDepartmentInList() {
        // given
        CurriculumDecider anotherDecider = new CurriculumDecider(MajorType.복수전공, department_컴퓨터공학과, 2020);
        List<CurriculumDecider> deciders = List.of(mainDecider, anotherDecider);

        // when & then
        assertThatThrownBy(() -> curriculumService.findCurriculumsByDecidersOnlyOthers(deciders))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("유효한 Decider 리스트로 여러 Curriculum 조회 성공")
    void findCurriculumsByDecidersOnlyOthers_ShouldReturnCurriculumList() {
        // given
        List<CurriculumDecider> deciders = List.of(otherDecider);
        List<Curriculum> curriculums = List.of(curriculum);

        when(curriculumRepository.findByDeciderIn(any(List.class))).thenReturn(curriculums);

        // when
        List<Curriculum> foundCurriculums = curriculumService.findCurriculumsByDecidersOnlyOthers(deciders);

        // then
        assertThat(foundCurriculums).isNotNull();
        assertThat(foundCurriculums).hasSize(1);
        assertThat(foundCurriculums).containsExactly(curriculum);
    }
}
