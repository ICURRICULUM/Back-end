/*
package icurriculum.domain.graduation.processor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.json.SwAiJson;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.processor.swai.CommonSwAiStrategy;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SwAiProcessorTest {

    CommonSwAiStrategy swAiProcessor;
    SwAiJson 전기공학과SwAiJson;
    SwAiJson 컴퓨터공학과SwAiJson;
    Take confirmedTake;
    Take alternativeTake;

    @BeforeEach
    void setUp() {
        swAiProcessor = new CommonSwAiStrategy();

        전기공학과SwAiJson = new SwAiJson(
            Set.of("GEE7002", "GEE7003", "GEE7004", "GEE7006", "GEE7007", "GEE7009", "GEE7010",
                "GEE7011"), Set.of("ACE2105", "ACE2103", "ACE1307"), 3);

        컴퓨터공학과SwAiJson = new SwAiJson(
            Set.of("GEE7006", "GEE7007", "GEE7008", "GEE7009", "GEE7010", "GEE7011", "GEE7012",
                "GEE7013", "GEE7014", "GEE7015", "GEE7016"), Collections.emptySet(), 3);

        confirmedTake = createTake("GEE7006", 3, Category.SW_AI);
        alternativeTake = createTake("ACE2105", 3, Category.SW_AI);
    }

    @Test
    @DisplayName("SW_AI가 필요하지 않을 때, 관련 과목을 듣지 않아도 클리어")
    void SW_AI_불필요_빈_리스트_테스트() {
        // given
        SwAiJson swAiJson = new SwAiJson(Collections.emptySet(), Collections.emptySet(), 0);
        List<Take> takes = Collections.emptyList();

        // when
        ProcessorResponse.SwAiDTO result = swAiProcessor.execute(swAiJson, takes);

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.completedCredit()).isEqualTo(0);
        assertThat(result.requiredCredits()).isEqualTo(0);
    }

    @Test
    @DisplayName("SW_AI 지정과목을 들었을 때 클리어")
    void SW_AI_지정과목_수강_클리어_테스트() {
        // given
        List<Take> takes = List.of(confirmedTake);

        // when
        ProcessorResponse.SwAiDTO result = swAiProcessor.execute(전기공학과SwAiJson, takes);

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.completedCredit()).isEqualTo(3);
        assertThat(result.requiredCredits()).isEqualTo(3);
    }

    @Test
    @DisplayName("SW_AI 대체과목을 들었을 때 클리어")
    void SW_AI_대체과목_수강_클리어_테스트() {
        // given
        List<Take> takes = List.of(alternativeTake);

        // when
        ProcessorResponse.SwAiDTO result = swAiProcessor.execute(전기공학과SwAiJson, takes);

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.completedCredit()).isEqualTo(3);
        assertThat(result.requiredCredits()).isEqualTo(3);
    }

    @Test
    @DisplayName("SW_AI 지정과목과 대체과목을 둘 다 들었지만 학점이 부족할 때 클리어되지 않음")
    void SW_AI_학점_부족_클리어_불가_테스트() {
        // given
        Take insufficientConfirmedTake = createTake("GEE7006", 1, Category.SW_AI); // 1학점 짜리 지정과목
        Take insufficientAlternativeTake = createTake("ACE2105", 1, Category.SW_AI); // 1학점 짜리 대체과목
        List<Take> takes = List.of(insufficientConfirmedTake, insufficientAlternativeTake);

        // when
        ProcessorResponse.SwAiDTO result = swAiProcessor.execute(전기공학과SwAiJson, takes);

        // then
        assertThat(result.isClear()).isFalse(); // 학점이 부족하여 클리어되지 않음
        assertThat(result.completedCredit()).isEqualTo(2); // 1 + 1 = 2학점
        assertThat(result.requiredCredits()).isEqualTo(3); // 필요한 학점은 3학점
    }

    @Test
    @DisplayName("SW_AI 대체과목 여러 개 중 일부만 수강했을 때 학점 계산")
    void SW_AI_대체과목_학점_계산_테스트() {
        // given
        Take alternativeTake1 = createTake("ACE2105", 2, Category.SW_AI); // 2학점 짜리 대체과목
        Take alternativeTake2 = createTake("ACE2103", 1, Category.SW_AI); // 1학점 짜리 대체과목
        List<Take> takes = List.of(alternativeTake1, alternativeTake2);

        // when
        ProcessorResponse.SwAiDTO result = swAiProcessor.execute(전기공학과SwAiJson, takes);

        // then
        assertThat(result.isClear()).isTrue(); // 총 3학점이므로 클리어됨
        assertThat(result.completedCredit()).isEqualTo(3); // 2 + 1 = 3학점
        assertThat(result.requiredCredits()).isEqualTo(3); // 필요한 학점은 3학점
    }

    @Test
    @DisplayName("SW_AI와 관련없는 수강 과목일 때 클리어되지 않음")
    void SW_AI_관련없는_과목_클리어_불가_테스트() {
        // given
        Take unrelatedTake = createTake("CSE9999", 3, Category.전공선택); // 전공선택 과목
        List<Take> takes = List.of(unrelatedTake);

        // when
        ProcessorResponse.SwAiDTO result = swAiProcessor.execute(컴퓨터공학과SwAiJson, takes);

        // then
        assertThat(result.isClear()).isFalse(); // SW_AI와 관련이 없으므로 클리어되지 않음
        assertThat(result.completedCredit()).isEqualTo(0); // 이수 학점은 0
        assertThat(result.requiredCredits()).isEqualTo(3); // 필요한 학점은 3학점
    }

    @Test
    @DisplayName("SW_AI 지정과목 중복 계산 방지")
    void SW_AI_지정과목_중복_계산_방지_테스트() {
        // given
        Take confirmedTake1 = createTake("GEE7006", 3, Category.SW_AI); // 동일한 SW_AI 과목을 두 번 수강한 케이스
        Take confirmedTake2 = createTake("GEE7006", 3, Category.SW_AI);
        List<Take> takes = List.of(confirmedTake1, confirmedTake2);

        // when
        ProcessorResponse.SwAiDTO result = swAiProcessor.execute(컴퓨터공학과SwAiJson, takes);

        // then
        assertThat(result.isClear()).isTrue(); // 학점이 충분하여 클리어됨
        assertThat(result.completedCredit()).isEqualTo(3); // 중복 계산 방지, 3학점만 계산
        assertThat(result.requiredCredits()).isEqualTo(3); // 필요한 학점은 3학점
    }

    @Test
    @DisplayName("SW_AI 대체과목 중복 계산 방지")
    void SW_AI_대체과목_중복_계산_방지_테스트() {
        // given
        Take alternativeTake1 = createTake("ACE2105", 3, Category.SW_AI);
        Take alternativeTake2 = createTake("ACE2105", 3, Category.SW_AI); // 동일한 대체 과목을 두 번 수강
        List<Take> takes = List.of(alternativeTake1, alternativeTake2);

        // when
        ProcessorResponse.SwAiDTO result = swAiProcessor.execute(전기공학과SwAiJson, takes);

        // then
        assertThat(result.isClear()).isTrue(); // 학점이 충분하여 클리어됨
        assertThat(result.completedCredit()).isEqualTo(3); // 중복 계산 방지, 3학점만 계산
        assertThat(result.requiredCredits()).isEqualTo(3); // 필요한 학점은 3학점
    }

    // Helper 메소드
    private Take createTake(String code, int credit, Category category) {
        return Take.builder().course(createCourse(code, credit)).category(category).build();
    }

    private Course createCourse(String code, int credit) {
        return Course.builder().code(code).credit(credit).build();
    }
}
*/
