package icurriculum.domain.take;

import icurriculum.domain.course.Course;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TakeTest {

    Member testMember;
    Course course;
    CustomCourse customCourse;

    @BeforeEach
    void setUp() {
        testMember = Member.builder().name("이승철").joinYear(19).role(RoleType.ROLE_USER).build();
        course = Course.builder().code("GEB1112").name("크로스오버 1 : 인간의 탐색").credit(2).build();
        customCourse = new CustomCourse("CSE9318", "현장실습 18", 18);
    }

    @Test
    @DisplayName("일반 Course가 포함된 Take test")
    void getEffectiveCourseWhenNormalCourse() {
        // given
        Take takeWithCourse = Take.builder()
                .category(Category.교양필수)
                .takenYear("2019")
                .takenSemester("1")
                .member(testMember)
                .course(course)
                .build();

        // when & then
        assertThat(takeWithCourse.getEffectiveCourse()).isEqualTo(course);
    }

    @Test
    @DisplayName("Custom Course가 포함된 Take test")
    void getEffectiveCourseWhenCustomCourse() {
        // given
        Take takeWithCustomCourse = Take.builder()
                .category(Category.전공선택)
                .takenYear("2023")
                .takenSemester("1")
                .member(testMember)
                .customCourse(customCourse)
                .build();

        // when & then
        Course effectiveCourse = takeWithCustomCourse.getEffectiveCourse();
        assertThat(effectiveCourse.getName()).isEqualTo(customCourse.getName());
        assertThat(effectiveCourse.getCode()).isEqualTo(customCourse.getCode());
        assertThat(effectiveCourse.getCredit()).isEqualTo(customCourse.getCredit());
    }

    @Test
    @DisplayName("Custom Course와 일반 Course 둘 다 있을 경우 에러 발생")
    void getEffectiveCourseWhenBothCourse() {
        // when & then
        assertThatThrownBy(() -> Take.builder()
                .category(Category.전공필수)
                .takenYear("2023")
                .takenSemester("1")
                .member(testMember)
                .course(course)
                .customCourse(customCourse)
                .build())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Custom Course와 일반 Course 둘 다 없을 경우 에러 발생")
    void getEffectiveCourseWhenNonCourse() {
        // when & then
        assertThatThrownBy(() -> Take.builder()
                .category(Category.전공필수)
                .takenYear("2023")
                .takenSemester("1")
                .member(testMember)
                .build())
                .isInstanceOf(RuntimeException.class);
    }
}
