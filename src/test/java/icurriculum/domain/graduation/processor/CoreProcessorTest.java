package icurriculum.domain.graduation.processor;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.json.CoreJson;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.CoreDto;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoreProcessorTest {

    private CoreProcessor coreProcessor;
    private Take take1;
    private Take take2;
    private Take take4;

    private Take alternativeTake;

    private Course course1;
    private Course course2;
    private Course course4;
    private Course altCourse;

    @BeforeEach
    void setUp() {
        coreProcessor = new CoreProcessor();

        take1 = mock(Take.class);
        take2 = mock(Take.class);
        take4 = mock(Take.class);

        course1 = mock(Course.class);
        course2 = mock(Course.class);
        course4 = mock(Course.class);
        altCourse = mock(Course.class);

        alternativeTake = mock(Take.class);

        mockTake(take1, course1, Category.핵심교양1, "GED1001", 3);
        mockTake(take2, course2, Category.핵심교양2, "GED2001", 3);
        mockTake(take4, course4, Category.핵심교양4, "GED4001", 3);
        mockTake(alternativeTake, altCourse, Category.교양선택, "ACE1301", 3);
    }

    private void mockTake(Take take, Course course, Category category, String courseCode, int credit) {
        when(take.getCategory()).thenReturn(category);
        when(take.getEffectiveCourse()).thenReturn(course);
        when(course.getCredit()).thenReturn(3);
        when(course.getCode()).thenReturn(courseCode);
    }

    @Test
    @DisplayName("핵심교양 영역이 확정되지 않은 경우 이수 학점이 필요한 학점 이상이면 클리어")
    void execute_shouldClearWhenAreaNotConfirmedAndCreditsSufficient() {
        // given
        CoreJson coreJson = new CoreJson(false, 9, Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap());
        List<Take> takes = List.of(take1, take2, take4);

        // when
        CoreDto result = coreProcessor.execute(coreJson, takes);

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.getCompletedCredit()).isEqualTo(9);
        assertThat(result.getRequiredCredits()).isEqualTo(9);
        assertThat(result.getUncompletedArea()).isEmpty();
    }

    @Test
    @DisplayName("핵심교양 영역이 확정되지 않았고 이수 학점이 부족한 경우 클리어되지 않음")
    void execute_shouldNotClearWhenAreaNotConfirmedAndCreditsInsufficient() {
        // given
        CoreJson coreJson = new CoreJson(false, 9, Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap());
        List<Take> takes = List.of(take1);

        // when
        CoreDto result = coreProcessor.execute(coreJson, takes);

        // then
        assertThat(result.isClear()).isFalse();
        assertThat(result.getCompletedCredit()).isEqualTo(3);
        assertThat(result.getRequiredCredits()).isEqualTo(9);
        assertThat(result.getUncompletedArea()).isEmpty();
    }

    @Test
    @DisplayName("핵심교양 영역이 확정되지 않은 경우 이수 학점이 필요한 학점 초과이면 클리어")
    void execute_shouldClearWhenAreaNotConfirmedAndCreditsOver() {
        // given
        CoreJson coreJson = new CoreJson(false, 9, Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap());
        List<Take> takes = List.of(take1, take1, take2, take4);

        // when
        CoreDto result = coreProcessor.execute(coreJson, takes);

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.getCompletedCredit()).isEqualTo(12);
        assertThat(result.getRequiredCredits()).isEqualTo(9);
        assertThat(result.getUncompletedArea()).isEmpty();
    }

    @Test
    @DisplayName("핵심교양 영역이 확정된 경우, 모든 필수 영역을 이수해야 클리어")
    void execute_shouldClearWhenAllRequiredAreasCompleted() {
        // given
        CoreJson coreJson = new CoreJson(true, 9, Set.of(Category.핵심교양1, Category.핵심교양2, Category.핵심교양4), Collections.emptyMap(), Collections.emptyMap());
        List<Take> takes = List.of(take1, take2, take4);

        // when
        CoreDto result = coreProcessor.execute(coreJson, takes);

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.getUncompletedArea()).isEmpty();
        assertThat(result.getRequiredCredits()).isEqualTo(9);
        assertThat(result.getCompletedCredit()).isEqualTo(9);
    }

    @Test
    @DisplayName("핵심교양 영역이 확정된 경우, 필수 영역 중 일부가 이수되지 않으면 클리어되지 않음")
    void execute_shouldNotClearWhenSomeRequiredAreasNotCompleted() {
        // given
        CoreJson coreJson = new CoreJson(true, 9, Set.of(Category.핵심교양1, Category.핵심교양2, Category.핵심교양4), Collections.emptyMap(), Collections.emptyMap());
        List<Take> takes = List.of(take1, take4);

        // when
        CoreDto result = coreProcessor.execute(coreJson, takes);

        // then
        assertThat(result.isClear()).isFalse();
        assertThat(result.getUncompletedArea()).contains(Category.핵심교양2);
        assertThat(result.getRequiredCredits()).isEqualTo(9);
        assertThat(result.getCompletedCredit()).isEqualTo(6);
    }

    @Test
    @DisplayName("핵심교양 영역이 확정된 경우, 요구학점을 초과했더라도 미이수 영역이 있다면 클리어되지 않음")
    void execute_shouldNotClearWhenCreditsSufficientButRequiredAreasNotCompleted() {
        // given
        CoreJson coreJson = new CoreJson(true, 9, Set.of(Category.핵심교양1, Category.핵심교양2, Category.핵심교양4), Collections.emptyMap(), Collections.emptyMap());
        List<Take> takes = List.of(take1, take1, take1, take4);

        // when
        CoreDto result = coreProcessor.execute(coreJson, takes);

        // then
        assertThat(result.isClear()).isFalse();
        assertThat(result.getUncompletedArea()).contains(Category.핵심교양2);
        assertThat(result.getRequiredCredits()).isEqualTo(9);
        assertThat(result.getCompletedCredit()).isEqualTo(12);
    }

/*    @Test
    @DisplayName("대체 과목이 인정되어 필수 영역을 모두 이수한 경우 클리어")
    void execute_shouldClearWhenAlternativeTakeIsAccepted() {
        // given
        Map<Category, Set<String>> alternativeCodesByArea = new HashMap<>();
        alternativeCodesByArea.put(Category.핵심교양4, Set.of("ACE1301"));

        CoreJson coreJson = new CoreJson(true, 9, Set.of(Category.핵심교양1, Category.핵심교양2, Category.핵심교양4), Collections.emptyMap(), alternativeCodesByArea);
        List<Take> takes = List.of(take1, take2, alternativeTake);

        // when
        CoreDto result = coreProcessor.execute(coreJson, takes);

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.getCompletedCredit()).isEqualTo(9);
        assertThat(result.getRequiredCredits()).isEqualTo(9);
        assertThat(result.getUncompletedArea()).isEmpty();
    }*/

    @Test
    @DisplayName("대체 과목이 인정되어 필수 영역을 모두 이수한 경우 클리어")
    void execute_shouldClearWhenAlternativeTakeIsAccepted() {
        // given
        when(alternativeTake.modifyCategory(any(Category.class))).thenAnswer(invocation -> {
            Category newCategory = invocation.getArgument(0);
            return Take.builder()
                    .category(newCategory)
                    .takenYear(null)
                    .takenSemester(null)
                    .member(null)
                    .course(altCourse)
                    .customCourse(null)
                    .build();
        });

        Map<Category, Set<String>> alternativeCodesByArea = new HashMap<>();
        alternativeCodesByArea.put(Category.핵심교양4, Set.of("ACE1301"));

        CoreJson coreJson = new CoreJson(true, 9, Set.of(Category.핵심교양1, Category.핵심교양2, Category.핵심교양4), Collections.emptyMap(), alternativeCodesByArea);
        List<Take> takes = List.of(take1, take2, alternativeTake);

        // when
        CoreDto result = coreProcessor.execute(coreJson, takes);

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.getCompletedCredit()).isEqualTo(9);
        assertThat(result.getRequiredCredits()).isEqualTo(9);
        assertThat(result.getUncompletedArea()).isEmpty();
    }

}
