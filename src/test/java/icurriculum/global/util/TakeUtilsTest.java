package icurriculum.global.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import icurriculum.domain.course.Course;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TakeUtilsTest {

    private Take 전공필수Take;
    private Take 전공선택Take;

    private Take 교양필수Take;
    private Take 교양선택Take;

    private Take SW_AITake;
    private Take 창의Take;

    private Take 핵심교양1Take;
    private Take 핵심교양2Take;
    private Take 핵심교양3Take;
    private Take 핵심교양4Take;
    private Take 핵심교양5Take;
    private Take 핵심교양6Take;

    private Take customTake;

    @BeforeEach
    void setUp() {
        Course 전공필수Course = mock(Course.class);
        when(전공필수Course.getCredit()).thenReturn(4);
        when(전공필수Course.getCode()).thenReturn("CSE2112");

        전공필수Take = mock(Take.class);
        when(전공필수Take.getCategory()).thenReturn(Category.전공필수);
        when(전공필수Take.getEffectiveCourse()).thenReturn(전공필수Course);

        Course 전공선택Course = mock(Course.class);
        when(전공선택Course.getCredit()).thenReturn(4);
        when(전공선택Course.getCode()).thenReturn("CSE3309");

        전공선택Take = mock(Take.class);
        when(전공선택Take.getCategory()).thenReturn(Category.전공선택);
        when(전공선택Take.getEffectiveCourse()).thenReturn(전공선택Course);

        Course 교양필수Course = mock(Course.class);
        when(교양필수Course.getCredit()).thenReturn(2);
        when(교양필수Course.getCode()).thenReturn("GEB1112");

        교양필수Take = mock(Take.class);
        when(교양필수Take.getCategory()).thenReturn(Category.교양필수);
        when(교양필수Take.getEffectiveCourse()).thenReturn(교양필수Course);

        Course 교양선택Course = mock(Course.class);
        when(교양선택Course.getCredit()).thenReturn(3);
        when(교양선택Course.getCode()).thenReturn("GEE5017");

        교양선택Take = mock(Take.class);
        when(교양선택Take.getCategory()).thenReturn(Category.교양선택);
        when(교양선택Take.getEffectiveCourse()).thenReturn(교양선택Course);

        Course swAiCourse = mock(Course.class);
        when(swAiCourse.getCredit()).thenReturn(3);
        when(swAiCourse.getCode()).thenReturn("GED6009");

        SW_AITake = mock(Take.class);
        when(SW_AITake.getCategory()).thenReturn(Category.SW_AI);
        when(SW_AITake.getEffectiveCourse()).thenReturn(swAiCourse);

        Course 창의Course = mock(Course.class);
        when(창의Course.getCredit()).thenReturn(3);
        when(창의Course.getCode()).thenReturn("GEE4010");

        창의Take = mock(Take.class);
        when(창의Take.getCategory()).thenReturn(Category.창의);
        when(창의Take.getEffectiveCourse()).thenReturn(창의Course);

        Course 핵심교양1Course = mock(Course.class);
        when(핵심교양1Course.getCredit()).thenReturn(3);
        when(핵심교양1Course.getCode()).thenReturn("GED1006");

        핵심교양1Take = mock(Take.class);
        when(핵심교양1Take.getCategory()).thenReturn(Category.핵심교양1);
        when(핵심교양1Take.getEffectiveCourse()).thenReturn(핵심교양1Course);

        Course 핵심교양2Course = mock(Course.class);
        when(핵심교양2Course.getCredit()).thenReturn(3);
        when(핵심교양2Course.getCode()).thenReturn("GED2006");

        핵심교양2Take = mock(Take.class);
        when(핵심교양2Take.getCategory()).thenReturn(Category.핵심교양2);
        when(핵심교양2Take.getEffectiveCourse()).thenReturn(핵심교양2Course);

        Course 핵심교양3Course = mock(Course.class);
        when(핵심교양3Course.getCredit()).thenReturn(3);
        when(핵심교양3Course.getCode()).thenReturn("GED3006");

        핵심교양3Take = mock(Take.class);
        when(핵심교양3Take.getCategory()).thenReturn(Category.핵심교양3);
        when(핵심교양3Take.getEffectiveCourse()).thenReturn(핵심교양3Course);

        Course 핵심교양4Course = mock(Course.class);
        when(핵심교양4Course.getCredit()).thenReturn(3);
        when(핵심교양4Course.getCode()).thenReturn("GED4006");

        핵심교양4Take = mock(Take.class);
        when(핵심교양4Take.getCategory()).thenReturn(Category.핵심교양4);
        when(핵심교양4Take.getEffectiveCourse()).thenReturn(핵심교양4Course);

        Course 핵심교양5Course = mock(Course.class);
        when(핵심교양5Course.getCredit()).thenReturn(3);
        when(핵심교양5Course.getCode()).thenReturn("GED5006");

        핵심교양5Take = mock(Take.class);
        when(핵심교양5Take.getCategory()).thenReturn(Category.핵심교양5);
        when(핵심교양5Take.getEffectiveCourse()).thenReturn(핵심교양5Course);

        Course 핵심교양6Course = mock(Course.class);
        when(핵심교양6Course.getCredit()).thenReturn(3);
        when(핵심교양6Course.getCode()).thenReturn("GED6006");

        핵심교양6Take = mock(Take.class);
        when(핵심교양6Take.getCategory()).thenReturn(Category.핵심교양6);
        when(핵심교양6Take.getEffectiveCourse()).thenReturn(핵심교양6Course);

        Course customCourse = mock(Course.class);
        when(customCourse.getCredit()).thenReturn(18);
        when(customCourse.getCode()).thenReturn("CSE9318");

        customTake = mock(Take.class);
        when(customTake.getCategory()).thenReturn(Category.전공선택);
        when(customTake.getEffectiveCourse()).thenReturn(customCourse);
    }

    @Test
    @DisplayName("핵심교양 카테고리만 필터링하여 반환")
    void getCoreTakes_ShouldReturnCoreTakesOnly() {
        // given
        List<Take> takes = List.of(전공필수Take, 전공선택Take,
            교양필수Take, 교양선택Take,
            핵심교양1Take, 핵심교양2Take, 핵심교양3Take, 핵심교양4Take, 핵심교양5Take, 핵심교양6Take,
            SW_AITake, 창의Take, customTake);

        // when
        List<Take> coreTakes = TakeUtils.getCoreTakes(takes);

        // then
        assertThat(coreTakes).hasSize(6);
        assertThat(coreTakes).containsExactlyInAnyOrder(핵심교양1Take, 핵심교양2Take, 핵심교양3Take, 핵심교양4Take,
            핵심교양5Take, 핵심교양6Take);
    }

    @Test
    @DisplayName("특정 카테고리에 해당하는 Take들만 반환")
    void getTakesByCategory_ShouldReturnTakesOfSpecificCategory() {
        // given
        List<Take> takes = List.of(전공필수Take, 전공선택Take,
            교양필수Take, 교양선택Take,
            핵심교양1Take, 핵심교양2Take, 핵심교양3Take, 핵심교양4Take, 핵심교양5Take, 핵심교양6Take,
            SW_AITake, 창의Take, customTake);

        // when
        List<Take> 전공필수Takes = TakeUtils.getTakesByCategory(takes, Category.전공필수);
        List<Take> 전공선택Takes = TakeUtils.getTakesByCategory(takes, Category.전공선택);
        List<Take> 교양필수Takes = TakeUtils.getTakesByCategory(takes, Category.교양필수);
        List<Take> 교양선택Takes = TakeUtils.getTakesByCategory(takes, Category.교양선택);
        List<Take> 핵심교양Takes = TakeUtils.getCoreTakes(takes);
        List<Take> SW_AITakes = TakeUtils.getTakesByCategory(takes, Category.SW_AI);
        List<Take> 창의Takes = TakeUtils.getTakesByCategory(takes, Category.창의);

        // then
        assertThat(전공필수Takes).hasSize(1);
        assertThat(전공필수Takes).containsExactlyInAnyOrder(전공필수Take);

        assertThat(전공선택Takes).hasSize(2);
        assertThat(전공선택Takes).containsExactlyInAnyOrder(전공선택Take, customTake);

        assertThat(교양필수Takes).hasSize(1);
        assertThat(교양필수Takes).containsExactlyInAnyOrder(교양필수Take);

        assertThat(교양선택Takes).hasSize(1);
        assertThat(교양선택Takes).containsExactlyInAnyOrder(교양선택Take);

        assertThat(핵심교양Takes).hasSize(6);
        assertThat(핵심교양Takes).containsExactlyInAnyOrder(핵심교양1Take, 핵심교양2Take, 핵심교양3Take, 핵심교양4Take,
            핵심교양5Take, 핵심교양6Take);

        assertThat(SW_AITakes).hasSize(1);
        assertThat(SW_AITakes).containsExactlyInAnyOrder(SW_AITake);

        assertThat(창의Takes).hasSize(1);
        assertThat(창의Takes).containsExactlyInAnyOrder(창의Take);

    }

    @Test
    @DisplayName("총 이수 학점을 올바르게 계산")
    void calculateTotalCredit_ShouldReturnTotalCredits() {
        List<Take> takes = List.of(전공필수Take, 전공선택Take, 교양필수Take, 교양선택Take, SW_AITake, 창의Take,
            customTake);

        int totalCredits = TakeUtils.calculateTotalCredit(takes);

        assertThat(totalCredits).isEqualTo(4 + 4 + 2 + 3 + 3 + 3 + 18);
    }

    @Test
    @DisplayName("Set을 사용해 총 이수 학점을 올바르게 계산")
    void calculateTotalCredit_WithSet_ShouldReturnTotalCredits() {
        // given
        Set<Take> takes = Set.of(전공필수Take, 전공선택Take, 교양필수Take, 교양선택Take, SW_AITake, 창의Take,
            customTake);

        // when
        int totalCredits = TakeUtils.calculateTotalCredit(takes);

        // then
        assertThat(totalCredits).isEqualTo(4 + 4 + 2 + 3 + 3 + 3 + 18);
    }

    @Test
    @DisplayName("Take 리스트에서 Course 코드를 추출하여 Set으로 반환")
    void extractCodes_ShouldReturnSetOfCourseCodes() {
        // given
        List<Take> takes = List.of(전공필수Take, 전공선택Take, 교양필수Take, 교양선택Take, SW_AITake, 창의Take,
            customTake);

        // when
        Set<String> codes = TakeUtils.extractCodes(takes);

        // then
        assertThat(codes).hasSize(7);
        assertThat(codes).containsExactlyInAnyOrder("CSE2112", "CSE3309", "GEB1112", "GEE5017",
            "GED6009", "GEE4010", "CSE9318");
    }

    @Test
    @DisplayName("특정 과목 코드 또는 대체 과목 코드가 이수되었는지 확인")
    void isTakenOrAlternativeTaken_ShouldReturnTrueIfCodeOrAlternativeIsTaken() {
        // given
        Set<String> takenCodes = Set.of("CSE3209");
        Set<String> alternativeCodes = Set.of("CSE3209");

        // when
        boolean isTaken = TakeUtils.isTakenOrAlternativeTaken("CSE2103", alternativeCodes,
            takenCodes);

        // then
        assertThat(isTaken).isTrue();
    }

}