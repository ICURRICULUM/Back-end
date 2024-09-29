package icurriculum.domain.curriculum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CurriculumDeciderTest {

    @Test
    @DisplayName("빌더를 통해 CurriculumDecider 객체를 생성할 때 필드가 올바르게 설정되는지 테스트")
    void 빌더로_CurriculumDecider_객체_생성시_필드_설정_테스트() {
        // given
        CurriculumDecider curriculumDecider = CurriculumDecider.builder()
            .majorType(MajorType.주전공)
            .departmentName(DepartmentName.컴퓨터공학과)
            .joinYear(2021)
            .build();

        // then
        assertThat(curriculumDecider.getMajorType())
            .isEqualTo(MajorType.주전공);
        assertThat(curriculumDecider.getDepartmentName())
            .isEqualTo(DepartmentName.컴퓨터공학과);
        assertThat(curriculumDecider.getJoinYear())
            .isEqualTo(2021);
    }

    @Test
    @DisplayName("유효성 검사 실패 시 예외 발생 테스트 - majorType이 null인 경우")
    void 유효성_검사_실패시_예외_발생_테스트_majorType_null() {
        // when & then
        assertThatThrownBy(() -> CurriculumDecider.builder()
            .departmentName(DepartmentName.컴퓨터공학과)
            .joinYear(2021)
            .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus",
                ErrorStatus.CURRICULUM_DECIDER_MISSING_VALUE);
    }

    @Test
    @DisplayName("유효성 검사 실패 시 예외 발생 테스트 - departmentName이 null인 경우")
    void 유효성_검사_실패시_예외_발생_테스트_departmentName_null() {
        // when & then
        assertThatThrownBy(() -> CurriculumDecider.builder()
            .majorType(MajorType.주전공)
            .joinYear(2021)
            .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus",
                ErrorStatus.CURRICULUM_DECIDER_MISSING_VALUE);
    }

    @Test
    @DisplayName("유효성 검사 실패 시 예외 발생 테스트 - joinYear이 null인 경우")
    void 유효성_검사_실패시_예외_발생_테스트_joinYear_null() {
        // when & then
        assertThatThrownBy(() -> CurriculumDecider.builder()
            .majorType(MajorType.주전공)
            .departmentName(DepartmentName.컴퓨터공학과)
            .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus",
                ErrorStatus.CURRICULUM_DECIDER_MISSING_VALUE);
    }
}
