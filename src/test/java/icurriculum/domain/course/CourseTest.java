package icurriculum.domain.course;

import icurriculum.data.TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CourseTest {

    private TestDataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        dataInitializer = new TestDataInitializer();
    }

    @Test
    @DisplayName("Course 객체가 정상적으로 생성되는지 테스트")
    void 코스객체생성_테스트() {
        // given
        Course course = dataInitializer.getCourseData();

        // when
        String code = course.getCode();
        String name = course.getName();
        Integer credit = course.getCredit();

        // then
        assertThat(code).isEqualTo("GEB1112");
        assertThat(name).isEqualTo("크로스오버 1 : 인간의 탐색");
        assertThat(credit).isEqualTo(2);
    }

    @Test
    @DisplayName("Course 객체의 equals 메소드가 제대로 동작하는지 테스트")
    void 코스객체_equals_테스트() {
        // given
        Course course1 = Course.builder().code("GEB1112").name("크로스오버 1 : 인간의 탐색").credit(2).build();
        Course course2 = Course.builder().code("GEB1112").name("크로스오버 1 : 인간의 탐색").credit(2).build();

        // when & then
        assertThat(course1).isEqualTo(course2);
    }

    @Test
    @DisplayName("Course 객체의 hashCode 메소드가 제대로 동작하는지 테스트")
    void 코스객체_hashCode_테스트() {
        // given
        Course course1 = Course.builder().code("GEB1112").name("크로스오버 1 : 인간의 탐색").credit(2).build();
        Course course2 = Course.builder().code("GEB1112").name("크로스오버 1 : 인간의 탐색").credit(2).build();

        // when & then
        assertThat(course1.hashCode()).isEqualTo(course2.hashCode());
    }

    @Test
    @DisplayName("Course 객체의 toString 메소드가 제대로 동작하는지 테스트")
    void 코스객체_toString_테스트() {
        // given
        Course course = dataInitializer.getCourseData();

        // when
        String courseString = course.toString();

        // then
        assertThat(courseString).contains("GEB1112", "크로스오버 1 : 인간의 탐색", "2");
    }
}
