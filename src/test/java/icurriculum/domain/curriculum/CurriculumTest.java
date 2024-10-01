package icurriculum.domain.curriculum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CurriculumTest {

    @Test
    @DisplayName("빌더를 통해 Curriculum 객체를 생성할 때 필수 필드가 올바르게 설정되는지 테스트")
    void 빌더로_Curriculum_객체_생성시_필수_필드_설정_테스트() {
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

        // when
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

        // then
        assertThat(curriculum.getDecider()).isNotNull();
        assertThat(curriculum.getCore()).isNotNull();
        assertThat(curriculum.getSwAi()).isNotNull();
        assertThat(curriculum.getCreativity()).isNotNull();
        assertThat(curriculum.getMajorRequired()).isNotNull();
        assertThat(curriculum.getMajorSelect()).isNotNull();
        assertThat(curriculum.getGeneralRequired()).isNotNull();
        assertThat(curriculum.getRequiredCredit()).isNotNull();
        assertThat(curriculum.getAlternativeCourse()).isNotNull();
    }

    @Test
    @DisplayName("유효성 검사 실패 시 예외 발생 테스트 - 필수 필드가 null인 경우")
    void 유효성_검사_실패시_예외_발생_테스트_필수_필드_null() {
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

        // when & then
        assertThatThrownBy(() -> Curriculum.builder()
            .decider(decider)
            .core(core)
            .swAi(swAi)
            .creativity(creativity)
            .majorRequired(majorRequired)
            .majorSelect(majorSelect)
            .generalRequired(generalRequired)
            .requiredCredit(requiredCredit)
            .alternativeCourse(null)
            .build()
        )
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CURRICULUM_MISSING_VALUE);
    }
}
