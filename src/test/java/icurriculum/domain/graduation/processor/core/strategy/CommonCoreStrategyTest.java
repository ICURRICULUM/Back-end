/*
package icurriculum.domain.graduation.processor.core.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.json.CoreJson;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.member.Member;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonCoreStrategyTest {

    private CommonCoreStrategy commonCoreStrategy;
    private Take take1;
    private Take take2;
    private Take take4;

    private Course altCourse;

    DepartmentName departmentName;
    Integer joinYear;
    private ProcessorRequest.CoreDTO request;

    @BeforeEach
    void setUp() {
        commonCoreStrategy = new CommonCoreStrategy();

        take1 = mock(Take.class);
        take2 = mock(Take.class);
        take4 = mock(Take.class);

        Course course1 = mock(Course.class);
        Course course2 = mock(Course.class);
        Course course4 = mock(Course.class);
        altCourse = mock(Course.class);
        when(altCourse.getCredit()).thenReturn(3);
        when(altCourse.getCode()).thenReturn("ACE1301");

        mockTake(take1, course1, Category.핵심교양1, "GED1001", 3);
        mockTake(take2, course2, Category.핵심교양2, "GED2001", 3);
        mockTake(take4, course4, Category.핵심교양4, "GED4001", 3);

        departmentName = mock(DepartmentName.class);
        joinYear = 19;
    }

    @Test
    @DisplayName("핵심교양 영역이 확정되지 않은 경우 이수 학점이 필요한 학점 이상이면 클리어")
    void execute_shouldClearWhenAreaNotConfirmedAndCreditsSufficient() {
        // given
        CoreJson coreJson = new CoreJson(false, 9, Collections.emptySet(), Collections.emptyMap(),
            Collections.emptyMap());
        request = new ProcessorRequest.CoreDTO(coreJson, Collections.emptyMap(), departmentName,
            joinYear);
        List<Take> takes = List.of(take1, take2, take4);

        // when
        ProcessorResponse.CoreDTO result = commonCoreStrategy.execute(request,
            new LinkedList<>(takes));

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.completedCredit()).isEqualTo(9);
        assertThat(result.requiredCredits()).isEqualTo(9);
        assertThat(result.uncompletedArea()).isEmpty();
    }

    @Test
    @DisplayName("핵심교양 영역이 정해지지 않았고, 이수 학점이 부족한 경우 클리어되지 않음")
    void execute_shouldNotClearWhenAreaNotConfirmedAndCreditsInsufficient() {
        // given
        CoreJson coreJson = new CoreJson(false, 9, Collections.emptySet(), Collections.emptyMap(),
            Collections.emptyMap());
        request = new ProcessorRequest.CoreDTO(coreJson, Collections.emptyMap(), departmentName,
            joinYear);
        List<Take> takes = List.of(take1);

        // when
        ProcessorResponse.CoreDTO result = commonCoreStrategy.execute(request,
            new LinkedList<>(takes));

        // then
        assertThat(result.isClear()).isFalse();
        assertThat(result.completedCredit()).isEqualTo(3);
        assertThat(result.requiredCredits()).isEqualTo(9);
        assertThat(result.uncompletedArea()).isEmpty();
    }

    @Test
    @DisplayName("핵심교양 영역이 정해지지 않았고, 이수 학점이 필요한 학점 초과이면 클리어")
    void execute_shouldClearWhenAreaNotConfirmedAndCreditsOver() {
        // given
        CoreJson coreJson = new CoreJson(false, 6, Collections.emptySet(), Collections.emptyMap(),
            Collections.emptyMap());
        request = new ProcessorRequest.CoreDTO(coreJson, Collections.emptyMap(), departmentName,
            joinYear);
        List<Take> takes = List.of(take1, take2, take4);

        // when
        ProcessorResponse.CoreDTO result = commonCoreStrategy.execute(request,
            new LinkedList<>(takes));

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.completedCredit()).isEqualTo(9);
        assertThat(result.requiredCredits()).isEqualTo(6);
        assertThat(result.uncompletedArea()).isEmpty();
    }

    @Test
    @DisplayName("핵심교양 영역이 확정된 경우, 모든 필수 영역을 이수해야 클리어")
    void execute_shouldClearWhenAllRequiredAreasCompleted() {
        // given
        CoreJson coreJson = new CoreJson(true, 9,
            Set.of(Category.핵심교양1, Category.핵심교양2, Category.핵심교양4), Collections.emptyMap(),
            Collections.emptyMap());
        request = new ProcessorRequest.CoreDTO(coreJson, Collections.emptyMap(), departmentName,
            joinYear);
        List<Take> takes = List.of(take1, take2, take4);

        // when
        ProcessorResponse.CoreDTO result = commonCoreStrategy.execute(request,
            new LinkedList<>(takes));

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.uncompletedArea()).isEmpty();
        assertThat(result.requiredCredits()).isEqualTo(9);
        assertThat(result.completedCredit()).isEqualTo(9);
    }

    @Test
    @DisplayName("핵심교양 영역이 확정된 경우, 필수 영역 중 일부가 이수되지 않으면 클리어되지 않음")
    void execute_shouldNotClearWhenSomeRequiredAreasNotCompleted() {
        // given
        CoreJson coreJson = new CoreJson(true, 9,
            Set.of(Category.핵심교양1, Category.핵심교양2, Category.핵심교양4), Collections.emptyMap(),
            Collections.emptyMap());
        request = new ProcessorRequest.CoreDTO(coreJson, Collections.emptyMap(), departmentName,
            joinYear);
        List<Take> takes = List.of(take1, take4);

        // when
        ProcessorResponse.CoreDTO result = commonCoreStrategy.execute(request,
            new LinkedList<>(takes));

        // then
        assertThat(result.isClear()).isFalse();
        assertThat(result.uncompletedArea()).contains(Category.핵심교양2);
        assertThat(result.requiredCredits()).isEqualTo(9);
        assertThat(result.completedCredit()).isEqualTo(6);
    }

    @Test
    @DisplayName("핵심교양 영역이 확정된 경우, 미이수 영역이 있다면 클리어되지 않음")
    void execute_shouldNotClearWhenRequiredAreasNotCompleted() {
        // given
        CoreJson coreJson = new CoreJson(true, 12,
            Set.of(Category.핵심교양1, Category.핵심교양2, Category.핵심교양4, Category.핵심교양6),
            Collections.emptyMap(), Collections.emptyMap());
        request = new ProcessorRequest.CoreDTO(coreJson, Collections.emptyMap(), departmentName,
            joinYear);
        List<Take> takes = List.of(take1, take2, take4);

        // when
        ProcessorResponse.CoreDTO result = commonCoreStrategy.execute(request,
            new LinkedList<>(takes));

        // then
        assertThat(result.isClear()).isFalse();
        assertThat(result.uncompletedArea()).contains(Category.핵심교양6);
        assertThat(result.requiredCredits()).isEqualTo(12);
        assertThat(result.completedCredit()).isEqualTo(9);
    }

    @Test
    @DisplayName("대체 과목이 인정되어 필수 영역을 모두 이수한 경우 클리어")
    void execute_shouldClearWhenAlternativeTakeIsAccepted() {
        // given
        Member member = mock(Member.class);
        Take alternativeTake = Take.builder().category(Category.교양필수).takenYear("2021")
            .takenSemester("2").member(member).course(altCourse).customCourse(null).build();

        Map<Category, Set<String>> alternativeCodesByArea = new HashMap<>();
        alternativeCodesByArea.put(Category.핵심교양4, Set.of("ACE1301"));

        CoreJson coreJson = new CoreJson(true, 9,
            Set.of(Category.핵심교양1, Category.핵심교양2, Category.핵심교양4), Collections.emptyMap(),
            alternativeCodesByArea);
        request = new ProcessorRequest.CoreDTO(coreJson, Collections.emptyMap(), departmentName,
            joinYear);
        List<Take> takes = List.of(take1, take2, alternativeTake);

        // when
        ProcessorResponse.CoreDTO result = commonCoreStrategy.execute(request,
            new LinkedList<>(takes));

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.completedCredit()).isEqualTo(9);
        assertThat(result.requiredCredits()).isEqualTo(9);
        assertThat(result.uncompletedArea()).isEmpty();
    }

    @Test
    @DisplayName("대체 과목과 영역 지정으로 클리어")
    void execute_shouldClearWhenAlternativeTakeAndConfirmedTakeIsAccepted() {
        Take confirmedTake = Take.builder().category(Category.핵심교양1).takenYear("2021")
            .takenSemester("2").course(altCourse).build();
        when(altCourse.getCode()).thenReturn("GED1002");

        Course course = mock(Course.class);
        Take alternativeTake = Take.builder().category(Category.교양필수).takenYear("2021")
            .takenSemester("2").course(course).build();
        when(course.getCredit()).thenReturn(3);
        when(course.getCode()).thenReturn("ACE1302");

        Map<Category, Set<String>> confirmedCodesByArea = new HashMap<>();
        confirmedCodesByArea.put(Category.핵심교양1, Set.of("GED1002"));

        Map<Category, Set<String>> alternativeCodesByArea = new HashMap<>();
        alternativeCodesByArea.put(Category.핵심교양6, Set.of("ACE1302"));

        CoreJson coreJson = new CoreJson(true, 12,
            Set.of(Category.핵심교양1, Category.핵심교양2, Category.핵심교양4, Category.핵심교양6),
            confirmedCodesByArea, alternativeCodesByArea);
        request = new ProcessorRequest.CoreDTO(coreJson, Collections.emptyMap(), departmentName,
            joinYear);
        List<Take> takes = List.of(confirmedTake, take2, take4, alternativeTake);

        // when
        ProcessorResponse.CoreDTO result = commonCoreStrategy.execute(request,
            new LinkedList<>(takes));

        // then
        assertThat(result.isClear()).isTrue();
        assertThat(result.completedCredit()).isEqualTo(12);
        assertThat(result.requiredCredits()).isEqualTo(12);
        assertThat(result.uncompletedArea()).isEmpty();
    }

    private void mockTake(Take take, Course course, Category category, String courseCode,
        int credit) {
        when(take.getCategory()).thenReturn(category);
        when(take.getEffectiveCourse()).thenReturn(course);
        when(course.getCredit()).thenReturn(credit);
        when(course.getCode()).thenReturn(courseCode);
    }
}*/
