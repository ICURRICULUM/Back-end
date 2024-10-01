package icurriculum.domain.curriculum.repository;

import static org.assertj.core.api.Assertions.assertThat;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.curriculum.data.MajorSelect;
import icurriculum.domain.curriculum.data.RequiredCredit;
import icurriculum.domain.curriculum.data.SwAi;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "de.flapdoodle.mongodb.embedded.version=5.0.5")
public class CurriculumRepositoryTest {

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Test
    @DisplayName("커리큘럼을 저장하고 findByDecider로 조회 테스트")
    void 커리큘럼_저장하고_조회_테스트() {
        // given
        CurriculumDecider decider = CurriculumDecider.builder()
            .majorType(MajorType.주전공)
            .departmentName(DepartmentName.컴퓨터공학과)
            .joinYear(2021)
            .build();

        Core core = Core.builder()
            .isAreaFixed(true)
            .requiredCredit(9)
            .build();

        SwAi swAi = SwAi.builder()
            .requiredCredit(3)
            .approvedCodeSet(Set.of("DUMMY"))
            .build();

        Creativity creativity = Creativity.builder()
            .requiredCredit(3)
            .approvedCodeSet(Set.of("DUMMY"))
            .build();

        MajorRequired majorRequired = MajorRequired.builder()
            .codeSet(Set.of("MR1001"))
            .build();

        MajorSelect majorSelect = MajorSelect.builder()
            .codeSet(Set.of("MS2001"))
            .build();

        GeneralRequired generalRequired = GeneralRequired.builder()
            .codeSet(Set.of("GR3001"))
            .build();

        RequiredCredit requiredCredit = RequiredCredit.builder()
            .totalNeedCredit(130)
            .singleNeedCredit(65)
            .secondNeedCredit(39)
            .minorNeedCredit(21)
            .build();

        AlternativeCourse alternativeCourse = AlternativeCourse.builder().build();

        Curriculum curriculum = Curriculum.builder()
            .decider(decider)
            .core(core)
            .swAi(swAi)
            .creativity(creativity)
            .majorRequired(majorRequired)
            .majorSelect(majorSelect)
            .generalRequired(generalRequired)
            .requiredCredit(requiredCredit)
            .alternativeCourse(alternativeCourse)
            .build();

        curriculumRepository.save(curriculum);

        // when
        Optional<Curriculum> findCurriculum = curriculumRepository.findByDecider(decider);

        // then
        assertThat(findCurriculum).isPresent();
        assertThat(findCurriculum.get().getDecider()).isEqualTo(decider);
    }

    @Test
    @DisplayName("존재하지 않는 커리큘럼을 조회할 때 빈 값을 반환하는지 테스트")
    void 존재하지_않는_커리큘럼_조회시_빈값_반환_테스트() {
        // given
        CurriculumDecider decider = CurriculumDecider.builder()
            .majorType(MajorType.부전공)
            .departmentName(DepartmentName.전기공학과)
            .joinYear(2022)
            .build();

        // when
        Optional<Curriculum> findCurriculum = curriculumRepository.findByDecider(decider);

        // then
        assertThat(findCurriculum).isNotPresent();
    }
}
