package icurriculum.domain.graduation.processor;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.json.SwAiJson;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.SwAiDto;
import icurriculum.domain.take.Take;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SwAiProcessorTest {

    private SwAiProcessor swAiProcessor;
    private Take take;
    private Course course;

    @BeforeEach
    void setUp() {
        swAiProcessor = new SwAiProcessor();
        take = mock(Take.class);
        course = mock(Course.class);
        when(take.getEffectiveCourse()).thenReturn(course);
        when(course.getCredit()).thenReturn(3);
    }

    @Test
    @DisplayName("SW_AI 이수 학점이 기준 학점 이상일 때 클리어")
    void execute_shouldClearWhenCreditsSufficient() {
        // given
        SwAiJson swAiJson = new SwAiJson(false, Collections.emptySet(), Collections.emptySet(), 3);
        List<Take> takes = List.of(take);

        // when
        SwAiDto result = swAiProcessor.execute(swAiJson, takes);

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.getCompletedCredit()).isEqualTo(3);
        assertThat(result.getRequiredCredits()).isEqualTo(3);
    }

    @Test
    @DisplayName("SW_AI 이수 학점이 기준 학점을 넘지 않을 때 클리어되지 않음")
    void execute_shouldNotClearWhenCreditsInsufficient() {
        // given
        SwAiJson swAiJson = new SwAiJson(false, Collections.emptySet(), Collections.emptySet(), 3);
        when(course.getCredit()).thenReturn(2);
        List<Take> takes = List.of(take);

        // when
        SwAiDto result = swAiProcessor.execute(swAiJson, takes);

        // then
        assertThat(result.isClear()).isFalse();
        assertThat(result.getCompletedCredit()).isEqualTo(2);
        assertThat(result.getRequiredCredits()).isEqualTo(3);
    }
}
