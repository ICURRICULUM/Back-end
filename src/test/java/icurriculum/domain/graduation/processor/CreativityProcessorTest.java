/*
package icurriculum.domain.graduation.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.json.CreativityJson;
import icurriculum.domain.graduation.processor.creativity.CreativityProcessor;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreativityProcessorTest {

    private CreativityProcessor creativityProcessor;
    private CreativityJson creativityJson;
    private Take creativityTake;
    private Take invalidCategoryTake;

    @BeforeEach
    void setUp() {
        creativityProcessor = new CreativityProcessor();

        creativityJson = new CreativityJson(
            Set.of("GEE4001", "GEE4002", "GEE4004", "GEE4005",
                "GEE4006", "GEE4007", "GEE4009", "GEE4010", "GEE4012",
                "GEE4013", "GEE4014", "GEE4015", "GEE4017", "GEE4018",
                "GEE4019", "GEE4020", "GEE4021", "GEE4025", "GEE4026"
            ),
            3);

        creativityTake = createTake("GEE4001", 3, Category.창의);
        invalidCategoryTake = createTake("CSE1001", 3, Category.창의);
    }

    @Test
    @DisplayName("창의 졸업요건 처리 - 성공 케이스")
    void creativeProcessor_성공_테스트() {
        // given
        List<Take> allTakeList = List.of(creativityTake);

        // when
        ProcessorResponse.CreativityDTO result = creativityProcessor
            .execute(creativityJson, allTakeList);

        // then
        assertThat(result.completedCredit()).isEqualTo(3);
        assertThat(result.requiredCredits()).isEqualTo(3);
        assertThat(result.isClear()).isTrue();
    }

    @Test
    @DisplayName("창의 졸업요건 처리 - 잘못된 카테고리 예외 발생")
    void creativeProcessor_잘못된_카테고리_예외_테스트() {
        // given
        List<Take> allTakeList = List.of(creativityTake, invalidCategoryTake);

        // then
        assertThatThrownBy(() -> creativityProcessor.execute(creativityJson, allTakeList))
            .isInstanceOf(GeneralException.class)
            .hasMessageContaining(ErrorStatus.CATEGORY_IS_NOT_VALID.getMessage())
            .extracting("data")
            .isEqualTo(invalidCategoryTake);
    }

    @Test
    @DisplayName("창의 졸업요건 처리 - 이수 학점 부족")
    void creativeProcessor_이수학점_부족_테스트() {
        // given
        List<Take> allTakeList = Collections.emptyList();

        // when
        ProcessorResponse.CreativityDTO result = creativityProcessor.execute(creativityJson,
            allTakeList);

        // then
        assertThat(result.completedCredit()).isEqualTo(0);
        assertThat(result.requiredCredits()).isEqualTo(3);
        assertThat(result.isClear()).isFalse();
    }

    @Test
    @DisplayName("창의 졸업요건 처리 - 이미 마킹된 과목 무시_실제로는 일어나지 않음")
    void creativeProcessor_마킹된_과목_무시_테스트_실제로는_일어나지_않음() {
        // given
        Take creativityTake = createTake("GEE4001", 3, Category.창의);
        creativityTake.mark();
        List<Take> allTakeList = List.of(creativityTake);

        // when
        ProcessorResponse.CreativityDTO result = creativityProcessor
            .execute(creativityJson, allTakeList);

        // then
        assertThat(result.completedCredit()).isEqualTo(0);
        assertThat(result.requiredCredits()).isEqualTo(3);
        assertThat(result.isClear()).isFalse();
    }

    @Test
    @DisplayName("창의 학점을 초과하여 이수했을 때도 클리어")
    void creativeProcessor_창의학점_초과이수_성공() {
        // given
        Take secondCreativityTake = createTake("GEE4001", 3, Category.창의);
        List<Take> allTakeList = List.of(creativityTake, secondCreativityTake);

        // when
        ProcessorResponse.CreativityDTO result = creativityProcessor.execute(creativityJson,
            allTakeList);

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.completedCredit()).isEqualTo(6);
        assertThat(result.requiredCredits()).isEqualTo(3);
    }

    private Take createTake(String code, int credit, Category category) {
        return Take.builder()
            .course(createCourse(code, credit))
            .category(category)
            .build();
    }

    private Course createCourse(String code, int credit) {
        return Course.builder()
            .code(code)
            .credit(credit)
            .build();
    }
}
*/
