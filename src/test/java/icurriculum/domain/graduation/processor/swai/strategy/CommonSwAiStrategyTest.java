/*
package icurriculum.domain.graduation.processor.swai.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.json.SwAiJson;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommonSwAiStrategyTest {

    private CommonSwAiStrategy swAiStrategy;

    @BeforeEach
    void setUp() {
        swAiStrategy = new CommonSwAiStrategy();
    }

    @Test
    @DisplayName("SW_AI 영역 이수 학점 계산 테스트")
    void testExecute() {
        // given
        LinkedList<Take> takeList = new LinkedList<>();
        takeList.add(createTake("SW001", Category.SW_AI, 3));  // SW_AI 과목
        takeList.add(createTake("EL001", Category.교양선택, 2)); // 교양선택 과목

        // Mock SwAiJson and request
        SwAiJson swAiJson = new SwAiJson(Set.of("SW002"), Set.of("SW003"), 6);
        Map<String, Set<String>> alternativeCourseMap = Map.of("SW002", Set.of("SW001"));

        ProcessorRequest.SwAiDTO request = Mockito.mock(ProcessorRequest.SwAiDTO.class);
        Mockito.when(request.swAiJson()).thenReturn(swAiJson);
        Mockito.when(request.alternativeCourseMap()).thenReturn(alternativeCourseMap);

        // when
        ProcessorResponse.SwAiDTO result = swAiStrategy.execute(request, takeList);

        // then
        assertThat(result.completedCredit()).isEqualTo(3); // SW_AI 학점만 계산
        assertThat(result.isClear()).isFalse();  // 6학점이 필요하므로 아직 클리어되지 않음
        assertThat(takeList).hasSize(1);  // SW_AI 과목은 삭제됨
    }

    @Test
    @DisplayName("대체 과목 포함된 경우 이수 학점 계산 테스트")
    void testExecuteWithAlternativeCourse() {
        // given
        LinkedList<Take> takeList = new LinkedList<>();
        takeList.add(createTake("SW002", Category.교양선택, 2));  // 교양선택 대체 과목
        takeList.add(createTake("SW003", Category.교양선택, 3));  // 교양선택 대체 과목

        // Mock SwAiJson and request
        SwAiJson swAiJson = new SwAiJson(Set.of("SW002"), Set.of("SW003"), 5);
        Map<String, Set<String>> alternativeCourseMap = Map.of("SW002", Set.of("SW003"));

        ProcessorRequest.SwAiDTO request = Mockito.mock(ProcessorRequest.SwAiDTO.class);
        Mockito.when(request.swAiJson()).thenReturn(swAiJson);
        Mockito.when(request.alternativeCourseMap()).thenReturn(alternativeCourseMap);

        // when
        ProcessorResponse.SwAiDTO result = swAiStrategy.execute(request, takeList);

        // then
        assertThat(result.completedCredit()).isEqualTo(5);  // 2 + 3 학점 계산
        assertThat(result.isClear()).isTrue();  // 요구 학점인 5학점 충족
        assertThat(takeList).hasSize(2);  // 대체 과목은 삭제되지 않음
    }

    private Take createTake(String code, Category category, int credit) {
        Course course = Course.builder()
            .code(code)
            .name("테스트 과목")
            .credit(credit)
            .build();
        return Take.builder()
            .category(category)
            .course(course)
            .build();
    }
}
*/
