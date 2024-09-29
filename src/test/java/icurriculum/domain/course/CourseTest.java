package icurriculum.domain.course;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CourseTest {

    Course course;
    String giveName = "크로스오버 1 : 인간의 탐색";
    String givenCode = "GEB1112";
    int givenCredit = 2;

    @BeforeEach
    void setUp() {
        course = Course.builder()
            .name(giveName)
            .code(givenCode)
            .credit(givenCredit)
            .build();
    }

    @Test
    @DisplayName("Course 객체가 정상적으로 생성되는지 테스트")
    void 코스객체생성_테스트() {
        // when
        String code = course.getCode();
        String name = course.getName();
        Integer credit = course.getCredit();

        // then
        assertThat(code).isEqualTo(givenCode);
        assertThat(name).isEqualTo(giveName);
        assertThat(credit).isEqualTo(givenCredit);
    }

    @Test
    @DisplayName("Course 객체의 equals 메소드가 제대로 동작하는지 테스트")
    void 코스객체_equals_테스트() {
        // given
        Course equalCourse = Course.builder()
            .code(givenCode)
            .name(giveName)
            .credit(givenCredit)
            .build();

        // when & then
        assertThat(course).isEqualTo(equalCourse);
    }

    @Test
    @DisplayName("Course 객체의 hashCode 메소드가 제대로 동작하는지 테스트")
    void 코스객체_hashCode_테스트() {
        // given
        Course equalCourse = Course.builder()
            .code(givenCode)
            .name(giveName)
            .credit(givenCredit)
            .build();

        // when & then
        assertThat(course.hashCode()).isEqualTo(equalCourse.hashCode());
    }

    @Test
    @DisplayName("Course 객체의 toString 메소드가 제대로 동작하는지 테스트")
    void 코스객체_toString_테스트() {
        // when
        String courseString = course.toString();

        // then
        assertThat(courseString).contains(givenCode, giveName, String.valueOf(givenCredit));
    }
}
