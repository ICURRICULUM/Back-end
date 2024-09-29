package icurriculum.domain.course.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.repository.CourseRepository;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        course1 = Course.builder()
            .code("GEB1112")
            .credit(2)
            .name("크로스오버 1 : 인간의 탐색")
            .build();

        course2 = Course.builder()
            .code("GEB1114")
            .credit(2)
            .name("크로스오버 3 : 사회의 탐색")
            .build();
    }

    @Test
    @DisplayName("코드 목록으로 여러 코스를 찾을 수 있다.")
    void 코드Set으로_코스리스트_정상반환() {
        // given
        Set<String> codes = Set.of(course1.getCode(), course2.getCode());
        when(courseRepository.findByCodeSet(codes)).thenReturn(List.of(course1, course2));

        // when
        List<Course> courses = courseService.getCourseListByCodeSet(codes);

        // then
        assertThat(courses).hasSize(2);
        assertThat(courses).containsExactlyInAnyOrder(course1, course2);
    }

}
