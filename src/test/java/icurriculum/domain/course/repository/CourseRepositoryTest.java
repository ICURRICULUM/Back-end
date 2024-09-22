package icurriculum.domain.course.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import icurriculum.data.TestDataInitializer;
import icurriculum.domain.course.Course;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    private TestDataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        dataInitializer = new TestDataInitializer(courseRepository);
        dataInitializer.initCourseData();
    }

    @Test
    @DisplayName("Course 객체가 정상적으로 저장되는지 테스트")
    void 코스저장_테스트() {
        // given
        String name = "공업수학 2";
        String code = "ACE2102";
        Integer credit = 3;
        Course course = Course.builder()
            .name(name)
            .code(code)
            .credit(credit)
            .build();

        // when
        Course savedCourse = courseRepository.save(course);

        // then
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getCode()).isEqualTo(code);
        assertThat(savedCourse.getName()).isEqualTo(name);
        assertThat(savedCourse.getCredit()).isEqualTo(credit);
    }

    @Test
    @DisplayName("Course 중복 저장 시 에러 발생 테스트")
    void 중복_Course_중복저장_테스트_Unique_제약_에러발생() {
        // given
        Course course = dataInitializer.getCourseData();

        // when & then
        assertThrows(DataIntegrityViolationException.class,
            () -> courseRepository.save(course));
    }

    @Test
    @DisplayName("Course 코드로 조회되는지 테스트")
    void 코스조회_테스트() {
        // given
        String courseCode = "GEB1112";

        // when
        Optional<Course> foundCourse = courseRepository.findByCode(courseCode);

        // then
        assertThat(foundCourse).isPresent();
        assertThat(foundCourse.get().getName()).isEqualTo("크로스오버 1 : 인간의 탐색");
        assertThat(foundCourse.get().getCredit()).isEqualTo(2);
    }

    @Test
    @DisplayName("모든 Course 목록 조회 테스트")
    void 모든_코스_조회_테스트() {
        // given
        List<Course> courseList = dataInitializer.getCourseDataList();

        // when
        List<Course> savedCourses = courseRepository.findAll();

        // then
        assertThat(savedCourses).hasSameSizeAs(courseList);
    }

    @Test
    @DisplayName("findByCodeSet 메소드가 정상적으로 동작하는지 테스트")
    void findByCodeSet_테스트() {
        // given
        Set<String> codes = Set.of("GEB1112", "GEB1114");

        // when
        List<Course> foundCourses = courseRepository.findByCodeSet(codes);

        // then
        assertThat(foundCourses).hasSize(2);
        assertThat(foundCourses).extracting(Course::getCode)
            .containsExactlyInAnyOrder("GEB1112", "GEB1114");
    }


}
